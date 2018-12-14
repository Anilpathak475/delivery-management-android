package co.parsl.android.boilerplate.ui.home

import android.app.ProgressDialog
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation
import co.parsl.android.boilerplate.remote.BufferooServiceFactory
import co.parsl.android.boilerplate.remote.ParslService
import co.parsl.android.boilerplate.remote.model.TagInfoPojo
import co.parsl.android.ui.BuildConfig
import co.parsl.android.ui.R
import kotlinx.android.synthetic.main.fragment_home.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment(), NfcTagListener {

    //TODO Inject Retrofit service
    val parslService : ParslService = BufferooServiceFactory.makeParslService(BuildConfig.DEBUG)

    override fun onNfcTagRead(message: String) {
        Toast.makeText(activity, "NFC Tag read - $message", Toast.LENGTH_LONG).show()
        getTagInfo()
    }

    private fun getTagInfo() {
        val call: Call<TagInfoPojo> = parslService.getTagInfo("2V4QK97b8pOd", getToken())

        val progressDialog = ProgressDialog(activity)
        progressDialog.setMessage("Getting tag info...")
        progressDialog.show()

        call.enqueue(object : Callback<TagInfoPojo> {
            override fun onFailure(call: Call<TagInfoPojo>, t: Throwable) {
                progressDialog.hide()
                Log.d("Retrofit", "onFailure: ")
            }

            override fun onResponse(call: Call<TagInfoPojo>, response: Response<TagInfoPojo>) {
                progressDialog.hide()
                if (response.isSuccessful) {
                    val action: HomeFragmentDirections.ActionHomeFragmentToTagInfoFragment = HomeFragmentDirections.actionHomeFragmentToTagInfoFragment(response.body()!!.uniqueId
                            ?: "", response.body()!!.assignedUrl ?: "", response.body()!!.status
                            ?: "", "", "", "", "", "")
                    navController.navigate(action)
                }
            }

        })

    }

    private fun getToken(): String {
        val idToken = MainActivity.readSharedSetting(activity!!.applicationContext, MainActivity.PREF_USER_ID_TOKEN, "")
        return "Bearer $idToken"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    private lateinit var navController: NavController

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        initListeners()
    }

    private fun initListeners() {

        Log.d("NFC", "<!--- NFC Setting listener")
        (activity as MainActivity).setNfcTagListener(this)

        assignButton.setOnClickListener {
            it.isEnabled = !it.isEnabled

            if (it.isEnabled) {
                enableAssign()
            } else {
                disableAssign()
            }
        }



        logoImage.setOnClickListener {
            getTagInfo()
        }
    }

    private fun disableAssign() {
        assignButton.isEnabled = false
        assignButton.background = resources.getDrawable(android.R.color.darker_gray, null)
    }

    private fun enableAssign() {
        assignButton.isEnabled = true
        assignButton.background = resources.getDrawable(R.color.parslLightGreen, null)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("NFC", "<!--- NFC setting listener as null")
        (activity as MainActivity).setNfcTagListener(null)
    }

}
