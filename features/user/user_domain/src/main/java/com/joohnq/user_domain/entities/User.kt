package com.joohnq.user_domain.entities

import com.joohnq.onboarding_domain.entities.AuthType

data class User(
				var id: String? = "",
				var authType: AuthType? = AuthType.NOT_ESPECIFIED,
				val name: String = "",
				val email: String = "",
				val imageUrl: String = "",
				val aboutMe: String = "",
				val skills: List<String> = mutableListOf(),
				val memberSince: String = "",
				val application: List<String> = mutableListOf(),
				val favourites: List<String> = mutableListOf()
)