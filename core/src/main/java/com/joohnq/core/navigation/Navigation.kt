package com.joohnq.core.navigation

import android.content.Context
import android.content.Intent

abstract class Navigation {
				open fun navigateToActivity(
								context: Context,
								activity: Class<*>,
				){
								Intent(context, activity).also {
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