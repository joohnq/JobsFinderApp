package com.joohnq.core.contracts

import android.net.Uri
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

object Contracts {
				fun getGalleryImagePicker(
								activity: AppCompatActivity,
								onImagePicked: (Uri) -> Unit
				): ActivityResultLauncher<PickVisualMediaRequest> {
								return activity.registerForActivityResult(
												ActivityResultContracts.PickVisualMedia()
								) { uri ->
												if (uri != null) {
																onImagePicked(uri)
												}
								}
				}
}