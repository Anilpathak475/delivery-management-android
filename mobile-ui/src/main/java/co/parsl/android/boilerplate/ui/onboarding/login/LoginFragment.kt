package co.parsl.android.boilerplate.ui.onboarding.login


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation
import co.parsl.android.boilerplate.ui.home.MainActivity
import co.parsl.android.boilerplate.ui.shared.AppHelper
import co.parsl.android.ui.R
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoDevice
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserSession
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.AuthenticationContinuation
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.AuthenticationDetails
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.ChallengeContinuation
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.MultiFactorAuthenticationContinuation
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.AuthenticationHandler
import kotlinx.android.synthetic.main.fragment_login.*
import org.koin.android.scope.ext.android.bindScope
import org.koin.android.scope.ext.android.getCurrentScope
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.concurrent.atomic.AtomicBoolean


class LoginFragment : Fragment() {

    private val viewModel: LoginViewModel by viewModel()
    private lateinit var findNavController: NavController
    private var status: AtomicBoolean = AtomicBoolean(false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindScope(getCurrentScope())
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
        AppHelper.init(activity)
        findNavController = Navigation.findNavController(view)
    }

    private fun initListeners() {
        loginButton.setOnClickListener {

            //hide keyboard
            val imm = activity!!.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(it.windowToken, 0)

            if (validateInput()) {
                loginCognito()
            } else {
                username.error = "Please verify credentials"
                password.error = "Please verify credentials"
            }
        }
    }

    private fun validateInput(): Boolean {
        return !TextUtils.isEmpty(username.text.toString()) && !TextUtils.isEmpty(password.text.toString())
    }

    private fun loginCognito() {

        if (status.compareAndSet(false, true)) {
            progressBar.visibility = View.VISIBLE

            Thread(object : Runnable {
                override fun run() {
                    invokeCognitoLogin()
                }

            }).start()
        }
    }

    private fun navigate(userSession: CognitoUserSession?) {
        activity?.runOnUiThread(object : Runnable {
            override fun run() {
                Log.d("cognito", "onSuccess")
                Log.d("cognito", "Access Token: ${userSession?.accessToken!!.jwtToken}")
                Log.d("cognito", "Refresh Token: ${userSession?.refreshToken!!.token}")
                Log.d("cognito", "Id Token: ${userSession?.idToken!!.jwtToken}")
                var logins = mapOf("cognito-idp.eu-west-1.amazonaws.com/eu-west-1_KCurLAn8H" to userSession?.idToken!!.jwtToken)

                progressBar.visibility = View.GONE
                status.set(false)

                activity!!.finish()
                MainActivity.saveSharedSetting(activity!!.applicationContext, MainActivity.PREF_USER_LOGGED_IN, "true")
                MainActivity.saveSharedSetting(activity!!.applicationContext, MainActivity.PREF_USER_ID_TOKEN, userSession?.idToken!!.jwtToken)
                startActivity(Intent(activity,MainActivity::class.java ))
                //findNavController.navigate(R.id.action_loginFragment_to_nav_graph_main)
            }
        })
    }

    private fun onLoginError(error: String) {
        status.set(false)
        progressBar.visibility = View.GONE
        MainActivity.saveSharedSetting(activity!!.applicationContext, MainActivity.PREF_USER_LOGGED_IN, "false")
        Toast.makeText(activity, "There are was some error while signing in. Please try again.", Toast.LENGTH_LONG).show()
    }

    private fun invokeCognitoLogin() {
        AppHelper.getPool().getUser(username.text.toString()).getSessionInBackground(object : AuthenticationHandler {
            override fun onSuccess(userSession: CognitoUserSession?, newDevice: CognitoDevice?) {
                this@LoginFragment.navigate(userSession)
            }

            override fun onFailure(exception: Exception?) {
                Log.d("cognito", "onFailure" + exception.toString())
                this@LoginFragment.onLoginError(exception.toString())
            }

            override fun getAuthenticationDetails(authenticationContinuation: AuthenticationContinuation, userId: String) {
                Log.d("cognito", "getAuthenticationDetails")
                getUserAuthentication(authenticationContinuation, userId)
            }

            override fun authenticationChallenge(continuation: ChallengeContinuation?) {
                Log.d("cognito", "authenticationChallenge")
            }

            override fun getMFACode(continuation: MultiFactorAuthenticationContinuation?) {
                Log.d("cognito", "getMFACode")
            }

        })
    }

    private fun getUserAuthentication(authenticationContinuation: AuthenticationContinuation, userId: String) {
        val authenticationDetails = AuthenticationDetails(username.text.toString(), password.text.toString(), null)
        authenticationContinuation.setAuthenticationDetails(authenticationDetails)
        authenticationContinuation.continueTask()
    }

}
