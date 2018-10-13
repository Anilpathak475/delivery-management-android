package org.buffer.android.boilerplate.ui.onboarding.register


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import org.buffer.android.boilerplate.ui.R
import org.koin.android.scope.ext.android.bindScope
import org.koin.android.scope.ext.android.getCurrentScope
import org.koin.android.viewmodel.ext.android.viewModel

class ConfirmationFragment : Fragment() {

    private val viewModel: ConfirmationViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindScope(getCurrentScope())
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_confirmation, container, false)
    }


}
