package com.joohnq.domain.entity

sealed class UiState<out T> {
				data object None: UiState<Nothing>()
				data object Loading: UiState<Nothing>()
				data class Success<out T>(val data: T): UiState<T>()
				data class Failure(val error: String?): UiState<Nothing>()

				companion object{
								fun <T> UiState<T>.getDataOrNull(): T? {
												return if (this is Success) data else null
								}

								fun <T> UiState<MutableList<T>>.add(data: T) {
												if (this is Success) this.data.add(data)
								}

								fun <T> UiState<List<T>>.toRecyclerViewState(
												take: Int? = null,
												onFailure: (String) -> Unit
								): RecyclerViewState<T> {
												return when (this) {
																is Success<List<T>> -> {
																				return if (data.isEmpty())
																								RecyclerViewState.Empty
																				else RecyclerViewState.Success(data.take(take ?: data.size))
																}

																is Failure -> {
																				onFailure(error.toString())
																				RecyclerViewState.Error(error)
																}

																is Loading -> RecyclerViewState.Loading
																is None -> RecyclerViewState.None
												}
								}
				}
}
