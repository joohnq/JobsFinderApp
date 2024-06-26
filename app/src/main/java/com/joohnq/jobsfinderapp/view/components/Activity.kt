package com.joohnq.jobsfinderapp.view.components

import android.app.Activity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.joohnq.jobsfinderapp.R

fun Activity.setOnApplyWindowInsetsListener(){
    ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
        val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
        v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
        insets
    }
}