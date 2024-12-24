package com.joohnq.ui.state

sealed class UiState<out T> {
				data object None: UiState<Nothing>()
				data object Loading: UiState<Nothing>()
				data class Success<out T>(val data: T): UiState<T>()
				data class Failure(val error: String?): UiState<Nothing>()

				companion object{
								fun <T> UiState<T>.getDataOrNull(): T? {
												return if (this is UiState.Success) data else null
								}

								fun <T> UiState<MutableList<T>>.add(data: T) {
												if (this is UiState.Success) this.data.add(data)
								}

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

																is UiState.Loading -> RecyclerViewState.Loading
																is UiState.None -> RecyclerViewState.None
												}
								}
				}
}
