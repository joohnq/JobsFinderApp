package com.joohnq.favorite.ui.navigation

import android.content.Context
import com.joohnq.core.domain.constant.Constants
import com.joohnq.core.domain.constant.ScreensConstants
import com.joohnq.job.domain.entity.Job

object FavoriteNavigationImpl: Navigation(),
				FavoriteNavigation {
				override fun navigateToJobDetailActivity(context: Context, job: Job) =
								navigateToActivityWithExtra(
												context,
												ScreensConstants.JOB_DETAIL_ACTIVITY,
												Constants.PARAMETER_JOB,
												job
								)
}