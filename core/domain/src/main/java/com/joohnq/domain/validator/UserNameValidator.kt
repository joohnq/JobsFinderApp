package com.joohnq.domain.validator

import com.joohnq.domain.exceptions.UserNameValidatorException

object UserNameValidator {
				operator fun invoke(username: String): Boolean {
								if (username.trim().isEmpty()) throw UserNameValidatorException.UserNameEmpty()
								return true
				}
}