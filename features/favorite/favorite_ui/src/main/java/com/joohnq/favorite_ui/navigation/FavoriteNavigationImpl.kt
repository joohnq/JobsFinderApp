package com.joohnq.favorite_ui.navigation

import android.content.Context
import com.joohnq.core.constants.Constants
import com.joohnq.core.navigation.FavoriteNavigation
import com.joohnq.core.navigation.Navigation
import com.joohnq.job_ui.activities.JobDetailActivity

object FavoriteNavigationImpl: Navigation(), FavoriteNavigation {
				override fun navigateToJobDetailActivity(context: Context, id: String) =
								navigateToActivityWithExtra(
												context,
												JobDetailActivity::class.java,
												Constants.PARAMETER_ID,
												id
								)
}