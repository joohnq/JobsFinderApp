package com.joohnq.home.navigation

import android.content.Context
import android.content.Intent
import com.joohnq.job_ui.activities.JobDetailActivity

object HomeNavigation {
				private fun navigateToActivity(
								context: Context,
								activity: Class<*>
				) = Intent(context, activity).also {
								context.startActivity(it)
				}

				private fun navigateToActivityWithExtra(
								context: Context,
								activity: Class<*>,
								key: String,
								value: String
				) = Intent(context, activity).also {
								it.putExtra(key, value)
								context.startActivity(it)
				}

				fun navigateToJobDetailActivity(context: Context, id: String) =
								navigateToActivityWithExtra(context, JobDetailActivity::class.java, "id", id)

				fun navigateToSearchActivity(context: Context) =
								navigateToActivity(context, JobDetailActivity::class.java)

				fun navigateToShowAllActivity(
								context: Context,
								showAllType: String
				) = navigateToActivityWithExtra(context, JobDetailActivity::class.java, "type", showAllType)
}