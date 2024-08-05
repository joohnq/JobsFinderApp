package com.joohnq.show_all_ui.navigation

import android.content.Context
import android.content.Intent
import com.joohnq.job_ui.activities.JobDetailActivity

object ShowAllNavigation {
				private fun navigateToActivityWithExtra(
								context: Context,
								activity: Class<*>,
								value: String
				) =
								Intent(context, activity).also {
												it.putExtra("id", value)
												context.startActivity(it)
								}

				fun navigateToJobDetailActivity(context: Context, id: String) =
								navigateToActivityWithExtra(context, JobDetailActivity::class.java, id)
}