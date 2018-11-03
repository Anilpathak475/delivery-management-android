package org.buffer.android.boilerplate.ui.onboarding

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.navigation.Navigation.findNavController
import com.amazonaws.mobileconnectors.cognitoidentityprovider.*
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.AuthenticationContinuation
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.AuthenticationDetails
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.ChallengeContinuation
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.MultiFactorAuthenticationContinuation
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.AuthenticationHandler
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.SignUpHandler
import org.buffer.android.boilerplate.ui.R
import org.buffer.android.boilerplate.ui.shared.AppHelper
import org.koin.android.scope.ext.android.bindScope
import org.koin.android.scope.ext.android.getCurrentScope
import org.koin.android.viewmodel.ext.android.viewModel
import java.lang.Exception

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