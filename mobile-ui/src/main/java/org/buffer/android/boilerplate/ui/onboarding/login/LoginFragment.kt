package org.buffer.android.boilerplate.ui.onboarding.login


import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoDevice
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserSession
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.AuthenticationContinuation
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.AuthenticationDetails
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.ChallengeContinuation
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.MultiFactorAuthenticationContinuation
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.AuthenticationHandler
import kotlinx.android.synthetic.main.fragment_login.*

import org.buffer.android.boilerplate.ui.R
import org.buffer.android.boilerplate.ui.shared.AppHelper
import org.koin.android.scope.ext.android.bindScope
import org.koin.android.scope.ext.android.getCurrentScope
import org.koin.android.viewmodel.ext.android.viewModel
import java.lang.Exception
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
            loginCognito()
        }
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
                findNavController.navigate(R.id.action_loginFragment_to_nav_graph_main)
            }
        })
    }

    private fun onLoginError() {
        status.set(false)
        progressBar.visibility = View.GONE
        Toast.makeText(activity, "There are was some error while signing in. Please try again.", Toast.LENGTH_LONG).show()
    }

    private fun invokeCognitoLogin() {
        AppHelper.getPool().getUser(username.text.toString()).getSessionInBackground(object : AuthenticationHandler {
            override fun onSuccess(userSession: CognitoUserSession?, newDevice: CognitoDevice?) {
                this@LoginFragment.navigate(userSession)
            }

            override fun onFailure(exception: Exception?) {
                Log.d("cognito", "onFailure" + exception.toString())
                onLoginError()
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
