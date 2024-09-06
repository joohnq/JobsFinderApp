package com.joohnq.core.navigation

import android.content.Context

abstract class MainNavigation: Navigation() {
				open fun navigateToOnboardingActivity(context: Context) {
								navigateToActivity(context, OnboardingNavigation::class.java)
				}
}