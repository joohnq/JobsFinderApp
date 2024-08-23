package com.joohnq.show_all_ui.navigation

import android.content.Context
import com.joohnq.core.constants.Constants
import com.joohnq.core.navigation.Navigation
import com.joohnq.core.navigation.ShowAllNavigation
import com.joohnq.job_ui.activities.JobDetailActivity

object ShowAllNavigationImpl: Navigation(), ShowAllNavigation {
				override fun navigateToJobDetailActivity(context: Context, id: String) =
								navigateToActivityWithExtra(
												context,
												JobDetailActivity::class.java,
												Constants.PARAMETER_ID,
												id
								)
}