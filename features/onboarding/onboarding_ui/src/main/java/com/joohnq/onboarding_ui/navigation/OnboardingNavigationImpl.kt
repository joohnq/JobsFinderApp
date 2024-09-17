package com.joohnq.onboarding_ui.navigation

import android.content.Context
import com.joohnq.core.constants.ScreensConstants
import com.joohnq.core.navigation.Navigation
import com.joohnq.core.navigation.OnboardingNavigation

object OnboardingNavigationImpl: Navigation(), OnboardingNavigation {
				override fun navigateToMainActivity(context: Context) =
								navigateToActivity(context, ScreensConstants.MAIN_ACTIVITY, true)

				override fun navigateToOccupationActivity(context: Context) =
								navigateToActivity(context, ScreensConstants.OCCUPATION_ACTIVITY)
}