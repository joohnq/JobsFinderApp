package com.joohnq.jobsfinderapp.model.repository

import com.joohnq.jobsfinderapp.model.entity.User
import com.joohnq.jobsfinderapp.util.UiState

interface UserRepository {
        fun getUserData(result: (UiState<User?>) -> Unit)
        fun getUserUid(result: (String?) -> Unit)
        fun updateUserToDatabase(user: User, result: (UiState<User?>) -> Unit)
}