package com.joohnq.jobsfinderapp.model.repository.auth

import com.joohnq.jobsfinderapp.model.entity.User
import com.joohnq.jobsfinderapp.util.UiState

interface AuthRepository {
    fun registerUser(user: User, password: String, result: (UiState<String>) -> Unit)
    fun loginUser(email: String, password: String, result: (UiState<String>) -> Unit)
    suspend fun logout()
    fun getUserFromDatabase(result: (User?) -> Unit)
    fun getUserUid(result: (String?) -> Unit)
    fun updateUserToDatabase(user: User, result: (UiState<String>) -> Unit)
}