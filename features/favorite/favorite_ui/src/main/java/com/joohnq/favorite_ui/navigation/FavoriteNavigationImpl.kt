package com.joohnq.favorite_ui.navigation

import android.content.Context
import com.joohnq.core.constants.Constants
import com.joohnq.core.constants.ScreensConstants
import com.joohnq.core.navigation.FavoriteNavigation
import com.joohnq.core.navigation.Navigation
import com.joohnq.job_domain.entities.Job

object FavoriteNavigationImpl: Navigation(), FavoriteNavigation {
				override fun navigateToJobDetailActivity(context: Context, job: Job) =
								navigateToActivityWithExtra(
												context,
												ScreensConstants.JOB_DETAIL_ACTIVITY,
												Constants.PARAMETER_JOB,
												job
								)
}