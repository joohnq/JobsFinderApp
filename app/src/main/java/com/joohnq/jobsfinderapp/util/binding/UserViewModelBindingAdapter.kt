package com.joohnq.jobsfinderapp.util.binding

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.joohnq.jobsfinderapp.model.entity.User
import com.joohnq.jobsfinderapp.util.UiState

object UserViewModelBindingAdapter {
    @JvmStatic
    @BindingAdapter("text")
    fun setText(view: TextView, uiState: UiState<User?>) {
        when (uiState) {
            is UiState.Loading -> {
            }
            is UiState.Success -> {
                uiState.data?.let {
                    view.text = it.name
                }
            }
            is UiState.Failure -> {
                view.text = "Error:"
            }
        }
    }
}