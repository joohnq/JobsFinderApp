package com.joohnq.user_data.network

import com.joohnq.user_domain.entities.User
import retrofit2.Response
import retrofit2.http.GET

interface UserService {
				@GET("/user")
				suspend fun get(): Response<User>
}