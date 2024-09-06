package com.joohnq.job_ui.navigation

import android.content.Context
import com.joohnq.core.navigation.JobDetailNavigation
import com.joohnq.core.navigation.Navigation

object JobDetailNavigationImpl: JobDetailNavigation, Navigation() {
				override fun navigateToJobUrl(context: Context, url: String) = navigateToWebLink(context, url)
}