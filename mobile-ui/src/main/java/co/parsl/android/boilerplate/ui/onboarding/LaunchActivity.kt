package co.parsl.android.boilerplate.ui.onboarding

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import androidx.navigation.Navigation.findNavController
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUser
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserAttributes
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserCodeDeliveryDetails
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.SignUpHandler
import co.parsl.android.boilerplate.ui.shared.AppHelper
import co.parsl.android.ui.R
import org.koin.android.scope.ext.android.bindScope
import org.koin.android.scope.ext.android.getCurrentScope
import org.koin.android.viewmodel.ext.android.viewModel

class LaunchActivity : AppCompatActivity() {

    private val launchViewModel: LaunchViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launch)
        setSupportActionBar(findViewById(R.id.my_toolbar))
        //supportActionBar?.setDisplayHomeAsUpEnabled(true)
        bindScope(getCurrentScope())
        //registerWithCognito()
    }

    override fun onSupportNavigateUp() = findNavController(this, R.id.nav_host_fragment).navigateUp()


    private fun registerWithCognito() {
        val userAttributes = CognitoUserAttributes()
        userAttributes.addAttribute("email", "renuka@theprocedure.in")
        AppHelper.getPool().signUpInBackground("captain_marvel", "Abcdef123", userAttributes, null, signUpHandler)
    }

    internal var signUpHandler: SignUpHandler = object : SignUpHandler {
        override fun onSuccess(user: CognitoUser, signUpConfirmationState: Boolean,
                               cognitoUserCodeDeliveryDetails: CognitoUserCodeDeliveryDetails) {
            // Check signUpConfirmationState to see if the user is already confirmed
            val regState = signUpConfirmationState
            if (signUpConfirmationState) {
                // User is already confirmed
                Log.d("cognito","Sign up successful!")
            } else {
                // User is not confirmed
                Log.d("cognito","User is not confirmed")
            }
        }

        override fun onFailure(exception: Exception) {
            Log.d("cognito","Sign up failed " + AppHelper.formatException(exception))
        }
    }


}