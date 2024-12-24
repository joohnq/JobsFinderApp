package com.joohnq.ui.navigation

import android.content.Context
import com.joohnq.domain.constant.ScreensConstants

object OnboardingNavigationImpl: Navigation(), OnboardingNavigation {
				override fun navigateToMainActivity(context: Context) =
								navigateToActivity(context, ScreensConstants.MAIN_ACTIVITY, true)

				override fun navigateToOccupationActivity(context: Context) =
								navigateToActivity(context, ScreensConstants.OCCUPATION_ACTIVITY)
}