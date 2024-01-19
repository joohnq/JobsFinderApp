package com.joohnq.jobsfinderapp.model.repository.auth.sign_in

import com.joohnq.jobsfinderapp.model.entity.User

data class SignInResult(
    val data: User?,
    val errorMessage: String?
)
