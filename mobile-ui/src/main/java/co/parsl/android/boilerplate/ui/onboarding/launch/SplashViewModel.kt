package co.parsl.android.boilerplate.ui.onboarding.launch

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import co.parsl.android.boilerplate.ui.onboarding.OnboardingState
import co.parsl.android.boilerplate.ui.shared.Event
import co.parsl.android.boilerplate.ui.shared.map

class SplashViewModel: ViewModel() {

    private val onBoardingCompletedResult = MutableLiveData<OnboardingState>()
    var launchDestination: LiveData<Event<LaunchDestination>>

    init {
        launchDestination = onBoardingCompletedResult.map {

            if ((it as? OnboardingState.PERMITTED)?.data == true) {
                Event(LaunchDestination.ON_BOARDING)
            } else {
                Event(LaunchDestination.MAIN_ACTIVITY)
            }
        }
    }

    enum class LaunchDestination {
        ON_BOARDING,
        MAIN_ACTIVITY
    }

    fun onPermissionsGranted() {
        onBoardingCompletedResult.postValue(OnboardingState.PERMITTED(true))
    }

    fun onPermissionsDenied() {
        onBoardingCompletedResult.postValue(OnboardingState.PERMITTED(false))
    }
}