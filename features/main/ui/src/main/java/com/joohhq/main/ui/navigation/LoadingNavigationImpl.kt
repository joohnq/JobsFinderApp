package com.joohhq.main.ui.navigation

import android.content.Context
import com.joohnq.core.domain.constant.ScreensConstants
import com.joohnq.ui.navigation.LoadingNavigation
import com.joohnq.ui.navigation.Navigation

object LoadingNavigationImpl: Navigation(), LoadingNavigation {
				override fun navigateToOnboardingActivity(context: Context) =
								navigateToActivity(context, ScreensConstants.ONBOARDING_ACTIVITY)

				override fun navigateToMainActivity(context: Context) =
								navigateToActivity(context, ScreensConstants.MAIN_ACTIVITY, true)

				override fun navigateToOccupationActivity(context: Context) =
								navigateToActivity(context, ScreensConstants.OCCUPATION_ACTIVITY)
}