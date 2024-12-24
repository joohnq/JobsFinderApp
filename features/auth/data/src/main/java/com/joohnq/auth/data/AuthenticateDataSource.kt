package com.joohnq.auth.data

import com.joohnq.auth.data.service.AuthenticationService
import retrofit2.Retrofit

class AuthenticateDataSource(
				private val retrofit: Retrofit
) {
				val authenticateService = retrofit.create(AuthenticationService::class.java)
}