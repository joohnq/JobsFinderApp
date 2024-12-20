package com.joohnq.main.navigation

import android.content.Context
import com.joohnq.core.constants.ScreensConstants
import com.joohnq.core.navigation.MainNavigation
import com.joohnq.core.navigation.Navigation
import com.joohnq.onboarding_ui.activities.OnboardingActivity

object MainNavigationImpl: Navigation(), MainNavigation {
				override fun navigateToOnboardingActivity(context: Context) {
								navigateToActivity(context, ScreensConstants.ONBOARDING_ACTIVITY, true)
				}
				override fun navigateToOccupationActivity(context: Context) {
								navigateToActivity(context, ScreensConstants.OCCUPATION_ACTIVITY, true)
				}
}