package com.joohnq.core.state

sealed class UiState<out T> {
				data object None : UiState<Nothing>()
				data object Loading : UiState<Nothing>()
				data class Success<out T>(val data: T) : UiState<T>()
				data class Failure(val error: String?) : UiState<Nothing>()
}

fun <T> UiState<T>.getDataOrNull(): T? {
				return if (this is UiState.Success) data else null
}
