package com.joohnq.ui.navigation

import android.content.Context
import com.joohnq.domain.entity.Job
import com.joohnq.domain.entity.ShowAllType

interface HomeNavigation {
				fun navigateToJobDetailActivity(context: Context, job: Job)
				fun navigateToProfileActivity(context: Context)
				fun navigateToShowAllActivity(
								context: Context,
								showAllType: ShowAllType
				)
}