package com.joohnq.core.navigation

import android.content.Context

interface OnboardingNavigation {
				fun navigateToMainActivity(context: Context)
				fun navigateToOccupationActivity(context: Context)
}