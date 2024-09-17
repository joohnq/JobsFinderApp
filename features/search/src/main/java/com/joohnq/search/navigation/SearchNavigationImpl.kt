package com.joohnq.search.navigation

import android.content.Context
import com.joohnq.core.constants.Constants
import com.joohnq.core.constants.ScreensConstants
import com.joohnq.core.navigation.Navigation
import com.joohnq.core.navigation.SearchNavigation
import com.joohnq.job_domain.entities.Job
import com.joohnq.job_ui.activities.JobDetailActivity

object SearchNavigationImpl: Navigation(), SearchNavigation {
				override fun navigateToJobDetailActivity(context: Context, job: Job) =
								navigateToActivityWithExtra(
												context,
												ScreensConstants.JOB_DETAIL_ACTIVITY,
												Constants.PARAMETER_JOB,
												job
								)
}