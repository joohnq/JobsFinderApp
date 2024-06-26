package com.joohnq.jobsfinderapp.util

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import java.io.ByteArrayOutputStream
import javax.inject.Singleton

class BitmapTransformation(private val context: Context) {
    fun Bitmap.bitmapToUriConverter(): Uri {
        val tempUri: Uri = getImageUri()
        return Uri.parse(tempUri.toString())
    }

    private fun Bitmap.getImageUri(): Uri {
        val bytes = ByteArrayOutputStream()
        compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path = MediaStore.Images.Media.insertImage(
            context.contentResolver,
            this,
            "Title",
            null
        )
        return Uri.parse(path)
    }
}