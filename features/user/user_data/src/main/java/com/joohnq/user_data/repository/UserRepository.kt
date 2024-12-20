package com.joohnq.user_data.repository

import android.net.Uri
import com.joohnq.user_domain.entities.User

interface UserRepository {
				suspend fun verifyIfEmailExists(email: String): Boolean
				suspend fun insertOrUpdate(user: User): Boolean
				suspend fun fetchUserImageUrl(): String
				suspend fun updateUserImageUrl(url: String): Boolean
				suspend fun getUser(): User
				suspend fun uploadUserImage(uri: Uri): Boolean
				suspend fun updateUserOccupation(occupation: String): Boolean
				suspend fun signOut(): Boolean
}
