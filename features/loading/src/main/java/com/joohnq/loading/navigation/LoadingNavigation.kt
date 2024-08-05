package com.joohnq.loading.navigation

import android.content.Context
import android.content.Intent
import com.joohnq.main.activities.MainActivity
import com.joohnq.onboarding_ui.activities.OnboardingActivity

object LoadingNavigation {
				private fun navigateToActivity(
								context: Context,
								activity: Class<*>,
				) = Intent(context, activity).also {
								context.startActivity(it)
				}

				fun navigateToOnboardingActivity(context: Context) =
								navigateToActivity(context, OnboardingActivity::class.java)

				fun navigateToMainActivity(context: Context) =
								navigateToActivity(context, MainActivity::class.java)
}