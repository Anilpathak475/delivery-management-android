package co.parsl.android.boilerplate.ui.home


import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import co.parsl.android.boilerplate.remote.BufferooServiceFactory
import co.parsl.android.boilerplate.remote.ParslService
import co.parsl.android.boilerplate.remote.model.Ledger
import co.parsl.android.ui.BuildConfig
import co.parsl.android.ui.R
import kotlinx.android.synthetic.main.fragment_scan_range.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


/**
 * A simple [Fragment] subclass.
 *
 */
class ScanRange : Fragment() {

    var handoverSend = "HANDOVER_SEND"
    var handoverReceive = "HANDOVER_RECEIVE"
    var handoverSendConfirm = "HANDOVER_SENDER_CONFIRM"
    var handoverReceiveConfirm = "HANDOVER_RECEIVER_CONFIRM"
    val parslService: ParslService = BufferooServiceFactory.makeParslService(BuildConfig.DEBUG)
    var t:Timer? = null
    private var action: String = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_scan_range, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListeners()
    }


    private fun initListeners() {
        sendHandover.setOnClickListener {
            action = handoverSend
            setHandOverAction(handoverSend)
        }
        receiveHandOver.setOnClickListener {
            action = handoverReceive
            setHandOverAction(handoverReceive)
        }
        sendHandoverConfirmButton.setOnClickListener {
            action = handoverSendConfirm
            setHandOverAction(handoverSendConfirm)

        }
        receiveHandoverConfirmButton.setOnClickListener {
            action = handoverReceiveConfirm
            setHandOverAction(handoverReceiveConfirm)
        }

    }

    private fun refreshLedger() {
        val call: Call<Ledger> = parslService.getLedger("7e6JwXzBkRgA", getToken())
        call.enqueue(object : Callback<Ledger> {
            override fun onFailure(call: Call<Ledger>, t: Throwable) {
                progressBar2.visibility = View.GONE
                Log.d("Retrofit", "onFailure: ")
            }

            override fun onResponse(call: Call<Ledger>, response: Response<Ledger>) {
                response.body()!!.elements.forEachIndexed { index, element ->
                    if (index == response.body()!!.elements.size - 1)
                        if (action == handoverSend) {
                            if (element.action == handoverReceive) {
                                handOverStatusTextView.visibility = View.GONE
                                progressBar2.visibility = View.GONE
                                t!!.cancel()
                                sendHandoverConfirmButton.visibility = View.VISIBLE
                            }
                        } else if (action == handoverReceive) {
                            if (element.action == handoverSendConfirm) {
                                handOverStatusTextView.visibility = View.GONE
                                progressBar2.visibility = View.GONE
                                t!!.cancel()
                                receiveHandoverConfirmButton.visibility = View.VISIBLE
                            }
                        }else if (action == handoverSendConfirm || action == handoverReceiveConfirm) {
                            if (element.action == handoverReceiveConfirm) {
                                handOverStatusTextView.visibility = View.VISIBLE
                                progressBar2.visibility = View.GONE
                                t!!.cancel()
                                handOverStatusTextView.text = "Handover Complete"
                            }
                        }
                }
            }

        })
    }

    private fun startPooling() {
//Set the schedule function and rate
        t =  Timer()
        t!!.scheduleAtFixedRate(object : TimerTask() {

            override fun run() {
                refreshLedger()
            }
        },
                //Set how long before to start calling the TimerTask (in milliseconds)
                0,
                //Set the amount of time between each execution (in milliseconds)
                10000)
    }

    private fun setHandOverAction(action: String) {
        this.action = action
        val call: Call<Void> = parslService.postHandoverAction("7e6JwXzBkRgA", action, getToken())

        call.enqueue(object : Callback<Void> {
            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.d("Retrofit", "onFailure: ")
            }

            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                sendHandover.visibility = View.GONE
                sendHandoverConfirmButton.visibility = View.GONE
                receiveHandOver.visibility = View.GONE
                receiveHandoverConfirmButton.visibility = View.GONE
                progressBar2.visibility = View.VISIBLE
                handOverStatusTextView.visibility = View.VISIBLE
                handOverStatusTextView.text = action
                startPooling()

            }

        })
    }

    private fun getToken(): String {
        val idToken = MainActivity.readSharedSetting(activity!!, MainActivity.PREF_USER_ID_TOKEN, "")
        return "Bearer $idToken"
    }


}
