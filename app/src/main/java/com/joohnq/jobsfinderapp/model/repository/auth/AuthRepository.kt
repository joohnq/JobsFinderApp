package com.joohnq.jobsfinderapp.model.repository.auth

import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.joohnq.jobsfinderapp.model.entity.User
import com.joohnq.jobsfinderapp.utils.UiState

interface AuthRepository {
    fun registerUser(user: User, password: String, result: (UiState<String>) -> Unit)
    fun loginUser(email: String, password: String, result: (UiState<String>) -> Unit)
    fun logout(result: () -> Unit)
    fun getUserUid(result: (String?) -> Unit)
}