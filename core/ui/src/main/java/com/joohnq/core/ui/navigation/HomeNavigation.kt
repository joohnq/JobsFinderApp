package com.joohnq.core.ui.navigation

import android.content.Context
import com.joohnq.job.domain.entity.Job
import com.joohnq.job.domain.entity.ShowAllType

interface HomeNavigation {
				fun navigateToJobDetailActivity(context: Context, job: Job)
				fun navigateToProfileActivity(context: Context)
				fun navigateToShowAllActivity(
								context: Context,
								showAllType: ShowAllType
				)
}