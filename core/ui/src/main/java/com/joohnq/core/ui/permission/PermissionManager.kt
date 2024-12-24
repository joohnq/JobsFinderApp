package com.joohnq.core.ui.permission

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class PermissionManager(private val context: Context) {
				fun verifyGalleryPermission(): Boolean {
								return ContextCompat.checkSelfPermission(
												context,
												verifyVersionSDKInt()
								) == PackageManager.PERMISSION_GRANTED
				}

				fun requestGalleryPermission(activity: Activity) {
								ActivityCompat.requestPermissions(
												activity,
												arrayOf(
																verifyVersionSDKInt()
												),
												GALLERY_PERMISSION_CODE
								)
				}

				private fun verifyVersionSDKInt(): String {
								return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
												Manifest.permission.READ_MEDIA_IMAGES
								} else {
												Manifest.permission.READ_EXTERNAL_STORAGE
								}
				}

				fun onRequestPermissionsResult(
								requestCode: Int,
								permission: Int,
								grantedResults: IntArray,
								onAllowPermission: () -> Unit,
								onDenyPermission: () -> Unit
				) {
								if (requestCode == permission) {
												if (grantedResults.firstOrNull() == PackageManager.PERMISSION_GRANTED) {
																onAllowPermission()
												} else {
																onDenyPermission()
												}
								}
				}

				companion object {
								const val GALLERY_PERMISSION_CODE = 1000
				}

}