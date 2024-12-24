package com.joohnq.domain.entities

import com.joohnq.ui.state.UiState
import com.joohnq.ui.state.getDataOrNull
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
				companion object{
								fun StateFlow<UiState<User>>.getOccupationOrNull(): String? {
												return value.getDataOrNull()?.occupation
								}
				}
}
