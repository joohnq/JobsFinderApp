package com.joohnq.core.state

import com.joohnq.job_domain.entities.Job

sealed class UiState<out T> {
				data object None: UiState<Nothing>()
				data object Loading: UiState<Nothing>()
				data class Success<out T>(val data: T): UiState<T>()
				data class Failure(val error: String?): UiState<Nothing>()
}

fun <T> UiState<T>.getDataOrNull(): T? {
				return if (this is UiState.Success) data else null
}

fun <T> UiState<MutableList<T>>.add(data: T) {
				if (this is UiState.Success) this.data.add(data)
}

fun UiState<MutableList<Job>>.remove(id: String) {
				if (this is UiState.Success) {
								data.find { it.id == id }.let { data.remove(it) }
				}
}