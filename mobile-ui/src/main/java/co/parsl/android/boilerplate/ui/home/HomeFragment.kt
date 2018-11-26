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
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_home.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment(), NfcTagListener {

    //TODO Inject Retrofit service
    val parslService : ParslService = BufferooServiceFactory.makeParslService(BuildConfig.DEBUG)

    override fun onNfcTagRead(message: String) {
        Toast.makeText(activity, "NFC Tag read - $message", Toast.LENGTH_LONG).show()
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
                val action: HomeFragmentDirections.ActionHomeFragmentToTagInfoFragment = HomeFragmentDirections.actionHomeFragmentToTagInfoFragment(response.body()!!.uniqueId?:"", response.body()!!.assignedUrl?:"" , response.body()!!.status?:"", "", "", "", "", "")
                navController.navigate(action)
            }

        })
    }

    private fun getToken(): String {
        val idToken = MainActivity.readSharedSetting(activity!!.applicationContext, MainActivity.PREF_USER_ID_TOKEN, "")
        return "Bearer $idToken" //"eyJraWQiOiJEancxc2JaUUN3dk8yU1gzUlk1QTZDVzAwU2RmU1FKbnJBR1hHcDQ3N3M0PSIsImFsZyI6IlJTMjU2In0.eyJzdWIiOiIyYjEwMWIzMS04MjJjLTQxYTItODYxNC0yZjZhMzNjZDc4MzEiLCJjb2duaXRvOmdyb3VwcyI6WyJwYXJzbHRlbmFudGRlbW8xIl0sImVtYWlsX3ZlcmlmaWVkIjp0cnVlLCJpc3MiOiJodHRwczpcL1wvY29nbml0by1pZHAuZXUtd2VzdC0xLmFtYXpvbmF3cy5jb21cL2V1LXdlc3QtMV9LQ3VyTEFuOEgiLCJjb2duaXRvOnVzZXJuYW1lIjoidGVzdDEiLCJhdWQiOiI2MnVocXJhaW45NTh1bjcycmF0NDVlbnU0MiIsImV2ZW50X2lkIjoiNzk1NzA0M2ItZGZjNC0xMWU4LThiMjQtNzlkMmIyNGE3NWVmIiwidG9rZW5fdXNlIjoiaWQiLCJzY29wZSI6InBhcnNsX3JvbGVzXC9oYW5kb3ZlciBwYXJzbF9yb2xlc1wvbGVkZ2VyLnIgcGFyc2xfcm9sZXNcL3JhbmdlLnIgcGFyc2xfcm9sZXNcL3N0YXR1cyBwYXJzbF9yb2xlc1wvdGFnLnIgcGFyc2xfcm9sZXNcL3RyYWNraW5nLnIiLCJhdXRoX3RpbWUiOjE1NDEyODk1NjgsImNvbXBhbnkiOiJjb21wYW55IiwiZXhwIjoxNTQxMzU2NzA0LCJpYXQiOjE1NDEzNTMxMDQsInRlbmFudCI6InBhcnNsdGVuYW50ZGVtbzEiLCJlbWFpbCI6InpvbHRhbi5uZWJsaUBkcGMuaHUifQ.SybbOyFqlPkBCmIq2Pz9nkIKFcOkGAfAuv8G8RX3txIm--x5a_8HhAZLAJYL3kRSuVy9Zgk60XTI1J-tLAS4n1231OT5IvL7ioxG0OVUt1F-X3H6lfdV1Z2hsMF2YYMxQQ6DEg7kyHGNh4UZnC-geGA00eykisj1NjSsIUleYLJI64rPgTqkQNF1pjW9GAiTsE02fQnBukZ6GWTcvKKlChjQAWseyZeCCfdISUksOcXyCx0wUaW1PiA1FK2qgM13nPs7IRyQaTL9pQX5U0Mf2aXFqFl4PVQn6SNXCs_WARd11MppaWQYwyovWuWQu7xmHDtH5QjofDF-_qUTyqZkIA"
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
        (activity as MainActivity)?.setNfcTagListener(this)

        assignButton.setOnClickListener {
            it.isEnabled = !it.isEnabled

            if (it.isEnabled) {
                enableAssign()
            } else {
                disableAssign()
            }
        }

        scanRangeButton.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_homeFragment_to_scanRange)
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
        (activity as MainActivity)?.setNfcTagListener(null)
    }

}
