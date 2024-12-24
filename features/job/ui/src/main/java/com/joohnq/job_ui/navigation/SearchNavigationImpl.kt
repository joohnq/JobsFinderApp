package com.joohnq.job_ui.navigation

import android.content.Context
import com.joohnq.domain.constant.Constants
import com.joohnq.domain.constant.ScreensConstants
import com.joohnq.domain.entity.Job

object SearchNavigationImpl: com.joohnq.ui.navigation.Navigation(),
				com.joohnq.ui.navigation.SearchNavigation {
				override fun navigateToJobDetailActivity(context: Context, job: Job) =
								navigateToActivityWithExtra(
												context,
												ScreensConstants.JOB_DETAIL_ACTIVITY,
												Constants.PARAMETER_JOB,
												job
								)
}