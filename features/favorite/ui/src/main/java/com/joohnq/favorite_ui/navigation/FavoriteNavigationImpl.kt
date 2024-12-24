package com.joohnq.favorite_ui.navigation

import android.content.Context
import com.joohnq.domain.constants.Constants
import com.joohnq.domain.constants.ScreensConstants
import com.joohnq.ui.navigation.FavoriteNavigation
import com.joohnq.ui.navigation.Navigation
import com.joohnq.domain.entity.Job

object FavoriteNavigationImpl: com.joohnq.ui.navigation.Navigation(),
				com.joohnq.ui.navigation.FavoriteNavigation {
				override fun navigateToJobDetailActivity(context: Context, job: Job) =
								navigateToActivityWithExtra(
												context,
												com.joohnq.domain.constants.ScreensConstants.JOB_DETAIL_ACTIVITY,
												com.joohnq.domain.constants.Constants.PARAMETER_JOB,
												job
								)
}