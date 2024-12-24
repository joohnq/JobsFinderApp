package com.joohnq.data.repository

import com.joohnq.domain.entity.response.SignInResponse
import com.joohnq.domain.entity.response.SignUpResponse

interface AuthenticationRepository {
				suspend fun signIn(email: String, password: String): SignInResponse
				suspend fun signUp(name: String, email: String, password: String): SignUpResponse
}