package com.joohnq.main.navigation

import android.content.Context
import com.joohnq.domain.constant.Constants
import com.joohnq.domain.constant.ScreensConstants
import com.joohnq.domain.entity.Job
import com.joohnq.domain.entity.ShowAllType
import com.joohnq.ui.navigation.HomeNavigation
import com.joohnq.ui.navigation.Navigation

object HomeNavigationImpl: Navigation(),
				HomeNavigation {
				override fun navigateToJobDetailActivity(context: Context, job: Job) =
								navigateToActivityWithExtra(
												context,
												ScreensConstants.JOB_DETAIL_ACTIVITY,
												Constants.PARAMETER_JOB,
												job
								)

				override fun navigateToShowAllActivity(
								context: Context,
								showAllType: ShowAllType
				) = navigateToActivityWithExtra(
								context,
								ScreensConstants.SHOW_ALL_ACTIVITY,
								"type",
								showAllType.toString()
				)

				override fun navigateToProfileActivity(context: Context): Unit =
								navigateToActivity(context, ScreensConstants.PROFILE_ACTIVITY)
}