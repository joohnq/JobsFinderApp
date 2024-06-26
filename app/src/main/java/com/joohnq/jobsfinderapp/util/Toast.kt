package com.joohnq.jobsfinderapp.util

import android.content.Context
import android.widget.Toast
import dagger.Component.Factory

class Toast(private val context: Context) : Toast(context) {
    fun invoke(str: String) {
        makeText(context, str, LENGTH_LONG).show()
    }
}