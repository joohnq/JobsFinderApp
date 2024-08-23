package com.joohnq.core.mappers

import androidx.lifecycle.MutableLiveData
import com.joohnq.core.state.RecyclerViewState
import com.joohnq.core.state.UiState

fun <T> UiState<List<T>>.toRecyclerViewState(
				take: Int? = null,
				onFailure: (String) -> Unit
): RecyclerViewState<T> {
				return when (this) {
								is UiState.Success<List<T>> -> {
												return if (data.isEmpty())
																RecyclerViewState.Empty
												else RecyclerViewState.Success(data.take(take ?: data.size))
								}

								is UiState.Failure -> {
												onFailure(error.toString())
												RecyclerViewState.Error(error)
								}

								else -> RecyclerViewState.Loading
				}
}

fun <T> MutableLiveData<UiState<T>>.setIfNewValue(state: UiState<T>) {
				if (this.value != state) {
								postValue(state)
				}
}
