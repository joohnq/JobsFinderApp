package com.joohhq.main.ui.navigation

import android.content.Context
import com.joohnq.core.domain.constant.ScreensConstants
import com.joohnq.ui.navigation.MainNavigation
import com.joohnq.ui.navigation.Navigation

object MainNavigationImpl: Navigation(),
				MainNavigation {
				override fun navigateToOnboardingActivity(context: Context) {
								navigateToActivity(context, ScreensConstants.ONBOARDING_ACTIVITY, true)
				}

				override fun navigateToOccupationActivity(context: Context) {
								navigateToActivity(context, ScreensConstants.OCCUPATION_ACTIVITY, true)
				}
}