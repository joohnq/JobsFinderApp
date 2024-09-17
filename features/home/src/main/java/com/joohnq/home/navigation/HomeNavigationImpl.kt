package com.joohnq.home.navigation

import android.content.Context
import com.joohnq.core.constants.Constants
import com.joohnq.core.constants.ScreensConstants
import com.joohnq.core.navigation.HomeNavigation
import com.joohnq.core.navigation.Navigation
import com.joohnq.job_domain.entities.Job
import com.joohnq.job_domain.entities.Teste
import com.joohnq.job_ui.activities.JobDetailActivity
import com.joohnq.profile.activities.ProfileActivity
import com.joohnq.show_all_domain.entities.ShowAllType
import com.joohnq.show_all_ui.activities.ShowAllActivity

object HomeNavigationImpl: Navigation(), HomeNavigation {
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