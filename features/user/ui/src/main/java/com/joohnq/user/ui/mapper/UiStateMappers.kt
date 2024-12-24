package com.joohnq.user.ui.mapper

import com.joohnq.core.domain.entity.UiState

inline fun <T> UiState<T>.fold(
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