package com.joohnq.core.navigation

import android.content.Context
import com.joohnq.job_domain.entities.Job
import com.joohnq.show_all_domain.entities.ShowAllType

interface HomeNavigation {
				fun navigateToJobDetailActivity(context: Context, job: Job)
				fun navigateToProfileActivity(context: Context)
				fun navigateToShowAllActivity(
								context: Context,
								showAllType: ShowAllType
				)
}