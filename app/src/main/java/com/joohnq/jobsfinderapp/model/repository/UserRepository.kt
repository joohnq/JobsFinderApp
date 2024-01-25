package com.joohnq.jobsfinderapp.model.repository

import android.net.Uri
import com.joohnq.jobsfinderapp.model.entity.User
import com.joohnq.jobsfinderapp.util.UiState

interface UserRepository {
    fun getUserData(result: (UiState<User?>) -> Unit)
    fun getUserUid(result: (String?) -> Unit)
    fun updateUserToDatabase(user: User, result: (UiState<User?>) -> Unit)
    suspend fun updateUserImage(uri: Uri?, result: (UiState<String?>) -> Unit)
    fun updateUserEmail(email: String, result: (UiState<String?>) -> Unit)
    fun getUserFromDatabase(user: User, result: (UiState<User?>) -> Unit)
    fun registerUserToDatabaseWithGoogle(user: User, result: (UiState<User?>) -> Unit)
}