package com.joohnq.auth.data.repository

import com.joohnq.auth.domain.entity.response.SignInResponse
import com.joohnq.auth.domain.entity.response.SignUpResponse

interface AuthenticationRepository {
				suspend fun signIn(email: String, password: String): SignInResponse
				suspend fun signUp(name: String, email: String, password: String): SignUpResponse
}