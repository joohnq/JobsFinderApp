package com.joohnq.core.navigation

import android.content.Context
import android.content.Intent

abstract class Navigation {
				open fun navigateToActivity(
								context: Context,
								activity: Class<*>,
								finish: Boolean = false
				){
								Intent(context, activity).also {
																it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
												context.startActivity(it)
								}
				}

				open fun navigateToActivityWithExtra(
								context: Context,
								activity: Class<*>,
								key: String,
								value: String
				) {
								Intent(context, activity).also {
												it.putExtra(key, value)
												context.startActivity(it)
								}
				}
}