package com.joohnq.data.network

import retrofit2.Retrofit
import javax.inject.Inject

class UserDataSource @Inject constructor(
				private val retrofit: Retrofit
) {
				val userService = retrofit.create(UserService::class.java)
}