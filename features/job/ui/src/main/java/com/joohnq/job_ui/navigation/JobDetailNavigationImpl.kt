package com.joohnq.job_ui.navigation

import android.content.Context
import com.joohnq.ui.navigation.JobDetailNavigation
import com.joohnq.ui.navigation.Navigation

object JobDetailNavigationImpl: JobDetailNavigation, Navigation() {
				override fun navigateToJobUrl(context: Context, url: String) = navigateToWebLink(context, url)
}