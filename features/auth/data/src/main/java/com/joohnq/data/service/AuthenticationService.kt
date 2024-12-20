package com.joohnq.data.service

import com.joohnq.domain.entities.request.SignInRequest
import com.joohnq.domain.entities.request.SignUpRequest
import com.joohnq.domain.entities.response.SignInResponse
import com.joohnq.domain.entities.response.SignUpResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthenticationService {
				@POST("/signin")
				suspend fun signIn(@Body request: com.joohnq.domain.entities.request.SignInRequest): Response<com.joohnq.domain.entities.response.SignInResponse>

				@POST("/signup")
				suspend fun signUp(@Body request: com.joohnq.domain.entities.request.SignUpRequest): Response<com.joohnq.domain.entities.response.SignUpResponse>
}