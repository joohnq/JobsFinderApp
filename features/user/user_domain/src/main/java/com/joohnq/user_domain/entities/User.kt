package com.joohnq.user_domain.entities

import androidx.lifecycle.LiveData
import com.joohnq.core.state.UiState
import com.joohnq.core.state.getDataOrNull
import com.joohnq.domain.entities.AuthType

data class User(
				val id: String? = null,
				val authType: AuthType = AuthType.EMAIL_PASSWORD,
				val name: String,
				val email: String,
				val imageUrl: String,
				val occupation: String,
				val favourites: List<String>
)

fun LiveData<UiState<User>>.getUserOccupationOrNull(): String? {
				return value?.getDataOrNull()?.occupation
}