package org.buffer.android.boilerplate.ui.onboarding

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.Navigation.findNavController
import org.buffer.android.boilerplate.ui.R
import org.buffer.android.boilerplate.ui.onboarding.LaunchViewModel.LaunchDestination.MAIN_ACTIVITY
import org.buffer.android.boilerplate.ui.onboarding.LaunchViewModel.LaunchDestination.ON_BOARDING
import org.buffer.android.boilerplate.ui.shared.EventObserver
import timber.log.Timber

class LaunchActivity: AppCompatActivity() {

    private lateinit var launchViewModel : LaunchViewModel //by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //todo inject ViewModel using Koin
        //bindScope(getCurrentScope())

        initFlow()

    }

    private fun initFlow() {

        launchViewModel = ViewModelProviders.of(this).get(LaunchViewModel::class.java)

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