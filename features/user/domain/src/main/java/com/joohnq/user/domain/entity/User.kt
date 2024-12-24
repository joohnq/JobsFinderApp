package com.joohnq.user.domain.entity

import com.joohnq.auth.domain.entity.AuthType
import com.joohnq.core.domain.entity.UiState
import com.joohnq.core.domain.entity.UiState.Companion.getDataOrNull
import kotlinx.coroutines.flow.StateFlow

data class User(
				val id: String? = null,
				val authType: AuthType = AuthType.EMAIL_PASSWORD,
				val name: String,
				val email: String,
				val imageUrl: String,
				val occupation: String,
				val favourites: List<String>
){
				companion object {
								fun StateFlow<UiState<User>>.getOccupationOrNull(): String? = value.getDataOrNull()?.occupation
				}
}
