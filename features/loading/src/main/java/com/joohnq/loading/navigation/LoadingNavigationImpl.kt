package com.joohnq.loading.navigation

import android.content.Context
import com.joohnq.core.navigation.LoadingNavigation
import com.joohnq.core.navigation.Navigation
import com.joohnq.main.activities.MainActivity
import com.joohnq.onboarding_ui.activities.OccupationActivity
import com.joohnq.onboarding_ui.activities.OnboardingActivity

object LoadingNavigationImpl: Navigation(), LoadingNavigation {
				override fun navigateToOnboardingActivity(context: Context) =
								navigateToActivity(context, OnboardingActivity::class.java)

				override fun navigateToMainActivity(context: Context) =
								navigateToActivity(context, MainActivity::class.java, true)

				override fun navigateToOccupationActivity(context: Context) =
								navigateToActivity(context, OccupationActivity::class.java)
}