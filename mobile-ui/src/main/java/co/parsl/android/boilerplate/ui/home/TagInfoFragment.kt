package co.parsl.android.boilerplate.ui.home


import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import co.parsl.android.boilerplate.ui.shared.AppHelper

import co.parsl.android.ui.R
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoDevice
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserSession
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.AuthenticationContinuation
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.ChallengeContinuation
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.MultiFactorAuthenticationContinuation
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.AuthenticationHandler
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_tag_info.*
import kotlinx.android.synthetic.main.fragment_welcome.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class TagInfoFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tag_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val uniqueIdValue = TagInfoFragmentArgs.fromBundle(arguments).uniqueId
        val assignedUrlValue = TagInfoFragmentArgs.fromBundle(arguments).assignedUrl
        val status = TagInfoFragmentArgs.fromBundle(arguments).status

        tagId.text = uniqueIdValue
        infoUrl.text = assignedUrlValue
        tagStatus.text = status
    }

    private fun initListeners() {
        sendButton.setOnClickListener {   }
        receiveButton.setOnClickListener {   }
        sendConfirm.setOnClickListener {   }
        receiveConfirmButton.setOnClickListener {   }
    }

    private fun invokeCognitoLogin() {
       // AppHelper.getPool().
    }
}
