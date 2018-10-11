package org.buffer.android.boilerplate.ui.onboarding

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import androidx.navigation.Navigation.findNavController
import org.buffer.android.boilerplate.ui.R
import org.buffer.android.boilerplate.ui.onboarding.LaunchViewModel.LaunchDestination.MAIN_ACTIVITY
import org.buffer.android.boilerplate.ui.onboarding.LaunchViewModel.LaunchDestination.ON_BOARDING
import org.buffer.android.boilerplate.ui.shared.EventObserver
import org.koin.android.scope.ext.android.bindScope
import org.koin.android.scope.ext.android.getCurrentScope
import org.koin.android.viewmodel.ext.android.viewModel
import timber.log.Timber

class LaunchActivity: AppCompatActivity() {

    val launchViewModel : LaunchViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bindScope(getCurrentScope())

        initFlow()

    }

    private fun initFlow() {

        launchViewModel.launchDestination.observe(this, EventObserver { destination ->
            when(destination) {
                ON_BOARDING -> Timber.d("ON_BOARDING")
                MAIN_ACTIVITY -> Timber.d("MAIN_ACTIVITY")
            }
        })
    }

    override fun onSupportNavigateUp()
            = findNavController(this, R.id.nav_host_fragment).navigateUp()

}