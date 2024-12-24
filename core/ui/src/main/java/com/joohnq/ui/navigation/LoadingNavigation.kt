package com.joohnq.ui.navigation

import android.content.Context

interface LoadingNavigation {
				fun navigateToOnboardingActivity(context: Context)
				fun navigateToMainActivity(context: Context)
				fun navigateToOccupationActivity(context: Context)
}