package com.joohnq.jobsfinderapp.util

import android.app.AlertDialog
import android.content.Context
import com.joohnq.jobsfinderapp.R
import dagger.Component.Factory

class AlertDialog(private val context: Context) : AlertDialog(context) {
    fun invoke(
        title: String,
        message: String,
        onPositiveClick: () -> Unit
    ){
        Builder(context)
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
}