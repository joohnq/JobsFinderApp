package com.joohnq.profile.navigation

import android.content.Context
import android.content.Intent
import com.joohnq.profile.activities.ProfileDetailActivity

object ProfileDetailNavigation {
				private fun navigateToActivity(
								context: Context,
								activity: Class<*>,
				) = Intent(context, activity).also {
								context.startActivity(it)
				}

				fun navigateToProfileDetailActivity(context: Context) =
								navigateToActivity(context, ProfileDetailActivity::class.java)
}