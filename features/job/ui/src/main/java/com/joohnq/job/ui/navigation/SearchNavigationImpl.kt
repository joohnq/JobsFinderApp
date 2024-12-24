package com.joohnq.job.ui.navigation

import android.content.Context
import com.joohnq.core.domain.constant.Constants
import com.joohnq.core.domain.constant.ScreensConstants
import com.joohnq.job.domain.entity.Job

object SearchNavigationImpl: Navigation(), SearchNavigation {
				override fun navigateToJobDetailActivity(context: Context, job: Job) =
								navigateToActivityWithExtra(
												context,
												ScreensConstants.JOB_DETAIL_ACTIVITY,
												Constants.PARAMETER_JOB,
												job
								)
}