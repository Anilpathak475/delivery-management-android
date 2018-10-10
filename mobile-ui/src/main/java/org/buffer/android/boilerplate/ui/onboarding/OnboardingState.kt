package org.buffer.android.boilerplate.ui.onboarding

import org.buffer.android.boilerplate.ui.model.ResourceState

open class OnboardingState(val resourceState: ResourceState,
                      val data: Boolean) {
    data class PERMITTED(val isPermitted: Boolean) : OnboardingState(ResourceState.SUCCESS, isPermitted)
}