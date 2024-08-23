package com.joohnq.onboarding_ui.navigation

import android.content.Context
import com.joohnq.core.navigation.Navigation
import com.joohnq.core.navigation.OnboardingNavigation
import com.joohnq.main.activities.MainActivity
import com.joohnq.onboarding_ui.activities.OccupationActivity

object OnboardingNavigationImpl: Navigation(), OnboardingNavigation {
				override fun navigateToMainActivity(context: Context) =
								navigateToActivity(context, MainActivity::class.java)

				override fun navigateToOccupationActivity(context: Context) =
								navigateToActivity(context, OccupationActivity::class.java)
}