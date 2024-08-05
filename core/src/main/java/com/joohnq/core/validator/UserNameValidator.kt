package com.joohnq.core.validator

import com.joohnq.core.exceptions.PasswordValidatorException
import com.joohnq.core.exceptions.UserNameValidatorException

object UserNameValidator {
				operator fun invoke(username: String): Boolean {
								if (username.isEmpty()) throw UserNameValidatorException.UserNameEmpty()
								if (username.isBlank()) throw UserNameValidatorException.UserNameInvalid()
								return true
				}
}