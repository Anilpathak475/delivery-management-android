package org.buffer.android.boilerplate.ui.onboarding

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import androidx.navigation.Navigation.findNavController
import org.buffer.android.boilerplate.ui.R
import org.buffer.android.boilerplate.ui.onboarding.LaunchViewModel.LaunchDestination.MAIN_ACTIVITY
import org.buffer.android.boilerplate.ui.onboarding.LaunchViewModel.LaunchDestination.ON_BOARDING
import org.buffer.android.boilerplate.ui.shared.EventObserver
import org.koin.android.scope.ext.android.bindScope
import org.koin.android.scope.ext.android.getCurrentScope
import org.koin.android.viewmodel.ext.android.viewModel
import timber.log.Timber

class LaunchActivity : AppCompatActivity() {

    private val launchViewModel: LaunchViewModel by viewModel()

    private val REQUEST_ID_MULTIPLE_PERMISSIONS = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bindScope(getCurrentScope())
        initFlow()
    }

    private fun initFlow() {

        checkForPermissions()

        launchViewModel.launchDestination.observe(this, EventObserver { destination ->
            when (destination) {
                ON_BOARDING -> handlePermissionsGranted()
                MAIN_ACTIVITY -> handlePermissionsDenied()
            }
        })
    }

    // region permissions
    private fun handlePermissionsGranted() {
        Timber.d("PERMISSIONS GRANTED")
    }

    private fun handlePermissionsDenied() {
        Toast.makeText(this, "Parsl can't proceed without permissions. Please, grant permissions :)", Toast.LENGTH_LONG).show()
        finish()
    }

    private fun checkForPermissions() {

        val nfcPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.NFC)
        val cameraPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
        val locationPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)

        val permissions = arrayListOf<String>()

        if (nfcPermission != PackageManager.PERMISSION_GRANTED) {
            permissions.add(Manifest.permission.NFC)
        }

        if (cameraPermission != PackageManager.PERMISSION_GRANTED) {
            permissions.add(Manifest.permission.CAMERA)
        }

        if (locationPermission != PackageManager.PERMISSION_GRANTED) {
            permissions.add(Manifest.permission.ACCESS_FINE_LOCATION)
        }

        handlePermissions(permissions)
    }

    private fun handlePermissions(permissions: ArrayList<String>) {
        if (permissions.isEmpty()) {
            launchViewModel.onPermissionsGranted()
        } else {
            requestPermissions(permissions.toTypedArray(), REQUEST_ID_MULTIPLE_PERMISSIONS)
        }
    }
    //endregion

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if(requestCode == REQUEST_ID_MULTIPLE_PERMISSIONS) {
            var permitted = true
            if (grantResults.isNotEmpty()) {
                for (result in grantResults) {
                    if (result != PackageManager.PERMISSION_GRANTED) {
                        permitted = false
                    }
                }
            }

            if (permitted) launchViewModel.onPermissionsGranted() else launchViewModel.onPermissionsDenied()
        }
    }

    override fun onSupportNavigateUp() = findNavController(this, R.id.nav_host_fragment).navigateUp()

}