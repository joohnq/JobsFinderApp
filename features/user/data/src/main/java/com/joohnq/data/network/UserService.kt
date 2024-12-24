package com.joohnq.data.network

import com.joohnq.domain.entity.MessageResponse
import com.joohnq.domain.entity.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.PUT
import retrofit2.http.Path

interface UserService {
				@GET("/user/{id}")
				suspend fun get(): Response<User>

				@PATCH("/user/{id}")
				suspend fun update(
								@Path("id") id: String,
								@Body updates: Map<String, Any>
				): Response<MessageResponse>

				@PUT("/user/{id}")
				suspend fun update(@Path("id") id: String, @Body user: User): Response<MessageResponse>
}