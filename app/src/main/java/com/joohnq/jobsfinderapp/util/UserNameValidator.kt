package com.joohnq.jobsfinderapp.util

import android.util.Patterns

sealed class UserNameValidatorException(message: String): Throwable(message) {
				data class UserNameIsEmpty(override val message: String = "Username is empty"):
								UserNameValidatorException(message)
}

class UserNameValidator {
				operator fun invoke(userName: String): Boolean {
								if (userName.isEmpty()) throw UserNameValidatorException.UserNameIsEmpty()
								return true
				}
}