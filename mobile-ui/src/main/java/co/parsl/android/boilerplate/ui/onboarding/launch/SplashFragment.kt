package co.parsl.android.boilerplate.ui.onboarding.launch


import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation
import co.parsl.android.boilerplate.ui.home.MainActivity
import co.parsl.android.boilerplate.ui.onboardingv2.WelcomeWizardActivity
import co.parsl.android.boilerplate.ui.shared.EventObserver
import co.parsl.android.ui.R
import org.koin.android.scope.ext.android.bindScope
import org.koin.android.scope.ext.android.getCurrentScope
import org.koin.android.viewmodel.ext.android.viewModel


class SplashFragment : Fragment() {

    private val splashViewModel: SplashViewModel by viewModel()
    private lateinit var navController: NavController
    private val REQUEST_ID_MULTIPLE_PERMISSIONS = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindScope(getCurrentScope())
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initFlow()
        navController = Navigation.findNavController(view)
    }

    private fun initFlow() {

        checkForPermissions()

        splashViewModel.launchDestination.observe(this, EventObserver { destination ->
            when (destination) {
                SplashViewModel.LaunchDestination.ON_BOARDING -> handlePermissionsGranted()
                SplashViewModel.LaunchDestination.MAIN_ACTIVITY -> handlePermissionsDenied()
            }
        })
    }

    // region permissions
    private fun handlePermissionsGranted() {
        //Check - Welcome Wizard
        invokeWelcomeWizard()
    }

    private fun invokeWelcomeWizard() {
        val isUserFirstTime = java.lang.Boolean.valueOf(MainActivity.readSharedSetting(activity!!.applicationContext, MainActivity.PREF_USER_FIRST_TIME, "true"))

        activity!!.finish()

        if (isUserFirstTime)
            startActivity(Intent(activity!!.applicationContext, WelcomeWizardActivity::class.java))

    }

    private fun handlePermissionsDenied() {
        Toast.makeText(requireActivity(), "Parsl can't proceed without permissions. Please, grant permissions :)", Toast.LENGTH_LONG).show()
        requireActivity().finishAffinity()
    }

    private fun checkForPermissions() {

        val nfcPermission = ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.NFC)
        val cameraPermission = ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA)
        val locationPermission = ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)

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
            splashViewModel.onPermissionsGranted()
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

            if (permitted) splashViewModel.onPermissionsGranted() else splashViewModel.onPermissionsDenied()
        }
    }
}
