package com.joohnq.user.ui.mappers

import com.joohnq.ui.state.UiState

inline fun <T> com.joohnq.ui.state.UiState<T>.fold(
				crossinline onFailure: (String?) -> Unit = {},
				crossinline onSuccess: (T) -> Unit = {},
				crossinline onLoading: () -> Unit = {},
				crossinline onNone: () -> Unit = {}
) {
				when (this) {
								is com.joohnq.ui.state.UiState.None -> onNone.invoke()
								is com.joohnq.ui.state.UiState.Failure -> onFailure.invoke(error)
								is com.joohnq.ui.state.UiState.Success -> onSuccess.invoke(data)
								is com.joohnq.ui.state.UiState.Loading -> onLoading.invoke()
				}
}