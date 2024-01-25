package com.joohnq.jobsfinderapp.util

import android.app.AlertDialog
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.viewbinding.ViewBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.joohnq.jobsfinderapp.R

object Functions {
    fun getTwoWords(str: String): String? {
        val palavras = str.split(" ")
        return if (palavras.size >= 2) {
            "${palavras[0]} ${palavras[1]}"
        } else {
            palavras[0]
        }
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

    fun customBottomSheet(
        context: Context,
        sheetBinding: ViewBinding
    ): BottomSheetDialog {
        val dialog = BottomSheetDialog(context)
        dialog.setContentView(sheetBinding.root)
        return dialog
    }

    fun showErrorWithToast(context: Context, tag: String, error: String?) {
        Toast.makeText(context, error, Toast.LENGTH_LONG).show()
        Log.e(tag, error.toString())
    }
}