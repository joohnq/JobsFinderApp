package com.joohnq.user_data.repository

import android.net.Uri
import com.google.gson.Gson
import com.joohnq.core.ErrorResponse
import com.joohnq.user_data.network.UserService
import com.joohnq.user_domain.entities.User

class UserRep(
				private val userService: UserService
): UserRepository {
				override suspend fun verifyIfEmailExists(email: String): Boolean {
								return true
				}

				override suspend fun insertOrUpdate(user: User): Boolean {
								return true
				}

				override suspend fun fetchUserImageUrl(): String {
								return ""
				}

				override suspend fun updateUserImageUrl(url: String): Boolean {
								return true
				}

				override suspend fun getUser(): User {
								val res = userService.get()
								if (!res.isSuccessful) {
												val error =
																Gson().fromJson<ErrorResponse>(res.errorBody()?.string(), ErrorResponse::class.java)
												throw Exception(error.message)
								}
								val body =
												res.body()
																?: throw Exception("Something went wrong with the content, try again later")
								return body
				}

				override suspend fun uploadUserImage(uri: Uri): Boolean {
								return true
				}

				override suspend fun updateUserOccupation(occupation: String): Boolean {
								return true
				}

				override suspend fun signOut(): Boolean {
								return true
				}
}