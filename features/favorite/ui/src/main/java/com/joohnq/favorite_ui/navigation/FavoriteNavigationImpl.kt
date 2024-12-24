package com.joohnq.favorite_ui.navigation

import android.content.Context
import com.joohnq.domain.constant.Constants
import com.joohnq.domain.constant.ScreensConstants
import com.joohnq.domain.entity.Job
import com.joohnq.ui.navigation.FavoriteNavigation
import com.joohnq.ui.navigation.Navigation

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