package com.joohnq.core.ui.navigation

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Parcelable

abstract class Navigation {
				open fun navigateToActivity(
								context: Context,
								activity: Class<*>,
								finish: Boolean = false
				) {
								Intent(context, activity).also {
												it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
												context.startActivity(it)
								}
				}

				open fun navigateToActivity(
								context: Context,
								className: String,
								finish: Boolean = false
				) {
								Intent(context, Class.forName(className)).also {
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

				open fun navigateToActivityWithExtra(
								context: Context,
								className: String,
								key: String,
								value: String
				) {
								Intent(context, Class.forName(className)).also {
												it.putExtra(key, value)
												context.startActivity(it)
								}
				}

				open fun navigateToActivityWithExtra(
								context: Context,
								activity: Class<*>,
								key: String,
								value: Parcelable
				) {
								Intent(context, activity).also {
												it.putExtra(key, value)
												context.startActivity(it)
								}
				}

				open fun navigateToActivityWithExtra(
								context: Context,
								className: String,
								key: String,
								value: Parcelable
				) {
								Intent(context, Class.forName(className)).also {
												it.putExtra(key, value)
												context.startActivity(it)
								}
				}

				open fun navigateToWebLink(context: Context, url: String) {
								Intent(Intent.ACTION_VIEW, Uri.parse(url)).also {
												context.startActivity(it)
								}
				}
}