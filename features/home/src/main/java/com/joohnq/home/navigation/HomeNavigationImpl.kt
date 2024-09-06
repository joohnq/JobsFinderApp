package com.joohnq.home.navigation

import android.content.Context
import com.joohnq.core.constants.Constants
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
												JobDetailActivity::class.java,
												Constants.PARAMETER_JOB,
												job
								)

				override fun navigateToShowAllActivity(
								context: Context,
								showAllType: ShowAllType
				) = navigateToActivityWithExtra(
								context,
								ShowAllActivity::class.java,
								"type",
								showAllType.toString()
				)

				override fun navigateToProfileActivity(context: Context): Unit =
								navigateToActivity(context, ProfileActivity::class.java)
}