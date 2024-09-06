package com.joohnq.user_domain.entities

import androidx.lifecycle.LiveData
import com.joohnq.core.state.UiState
import com.joohnq.core.state.getDataOrNull
import com.joohnq.onboarding_domain.entities.AuthType

data class User(
				var id: String? = "",
				var authType: AuthType? = AuthType.NOT_ESPECIFIED,
				val name: String = "",
				val email: String = "",
				val imageUrl: String = "",
				val occupation: String = "",
				val application: List<String> = mutableListOf(),
				val favourites: List<String> = mutableListOf()
)

fun LiveData<UiState<User>>.getUserOccupationOrNull(): String? {
				return value?.getDataOrNull()?.occupation
}