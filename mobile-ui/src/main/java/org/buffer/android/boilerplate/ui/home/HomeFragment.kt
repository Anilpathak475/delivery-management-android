package org.buffer.android.boilerplate.ui.home


import android.nfc.NfcAdapter
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.buffer.android.boilerplate.ui.R
import android.app.PendingIntent
import android.content.Intent
import android.app.Activity
import android.nfc.Tag
import android.nfc.tech.Ndef
import android.os.Parcelable
import android.util.Log


class HomeFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
    }

    private fun initListeners() {

    }

    override fun onResume() {
        super.onResume()
    }


}
