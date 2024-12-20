package com.joohnq.data.data_source

import com.joohnq.data.service.AuthenticationService
import retrofit2.Retrofit

class AuthenticateDataSource(
				private val retrofit: Retrofit
) {
				val authenticateService = retrofit.create(AuthenticationService::class.java)
}