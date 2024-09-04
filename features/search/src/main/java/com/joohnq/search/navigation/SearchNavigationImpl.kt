package com.joohnq.search.navigation

import android.content.Context
import com.joohnq.core.constants.Constants
import com.joohnq.core.navigation.Navigation
import com.joohnq.core.navigation.SearchNavigation
import com.joohnq.job_ui.activities.JobDetailActivity

object SearchNavigationImpl: Navigation(), SearchNavigation {
				override fun navigateToJobDetailActivity(context: Context, id: String) =
								navigateToActivityWithExtra(
												context,
												JobDetailActivity::class.java,
												Constants.PARAMETER_JOB,
												id
								)
}