package com.joohnq.jobsfinderapp.util

import android.app.AlertDialog
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.viewbinding.ViewBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.joohnq.jobsfinderapp.R
import java.io.ByteArrayOutputStream

object Functions {
    fun getOneWord(str: String): String? {
        val s = str.split(" ")
        return s[0]
    }

    fun bitmapToUriConverter(context: Context, bitmap: Bitmap): Uri {
        val tempUri: Uri = getImageUri(context.applicationContext, bitmap)
        return Uri.parse(tempUri.toString())
    }

    private fun getImageUri(inContext: Context, inImage: Bitmap): Uri {
        val bytes = ByteArrayOutputStream()
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path = MediaStore.Images.Media.insertImage(
            inContext.contentResolver,
            inImage,
            "Title",
            null
        )
        return Uri.parse(path)
    }

    fun customAlertDialog(
        context: Context,
        title: String,
        message: String,
        onPositiveClick: () -> Unit
    ) {
        AlertDialog.Builder(context)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(R.string.yes) { _, _ ->
                onPositiveClick.invoke()
            }
            .setNegativeButton(R.string.cancel) { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    fun <T> handleUiState(
        state: UiState<T>,
        onFailure: (String?) -> Unit = {},
        onSuccess: (T) -> Unit = {},
        onLoading: () -> Unit = {}
    ) {
        when (state) {
            is UiState.Failure -> onFailure.invoke(state.error)
            is UiState.Success -> onSuccess.invoke(state.data)
            is UiState.Loading -> onLoading.invoke()
        }
    }

    fun showErrorWithToast(context: Context, tag: String, error: String?) {
        Toast.makeText(context, error, Toast.LENGTH_LONG).show()
        Log.e(tag, error.toString())
    }
}