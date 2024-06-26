package com.joohnq.jobsfinderapp.util

sealed class UiState<out T> {
    data object None : UiState<Nothing>()
    data object Loading : UiState<Nothing>()
    data class Success<out T>(val data: T) : UiState<T>()
    data class Failure(val error: String?) : UiState<Nothing>()
}

inline fun <T> UiState<T>.handleUiState(
    crossinline onFailure: (String?) -> Unit = {},
    crossinline onSuccess: (T) -> Unit = {},
    crossinline onLoading: () -> Unit = {},
    crossinline onNone: () -> Unit = {}
) {
    when (this) {
        is UiState.None -> onNone.invoke()
        is UiState.Failure -> onFailure.invoke(error)
        is UiState.Success -> onSuccess.invoke(data)
        is UiState.Loading -> onLoading.invoke()
    }
}
