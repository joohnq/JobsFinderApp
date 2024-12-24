package com.joohnq.auth.domain.entity.request

data class SignUpRequest(
				val name: String,
				val email: String,
				val password: String
)
