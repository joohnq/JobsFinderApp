package com.joohnq.core.domain.entity

sealed class RecyclerViewState<out T> {
				data object None: RecyclerViewState<Nothing>()
				data object Empty: RecyclerViewState<Nothing>()
				data object Loading: RecyclerViewState<Nothing>()
				data class Success<out T>(val data: List<T>): RecyclerViewState<T>()
				data class Error(val error: String?): RecyclerViewState<Nothing>()
}