package com.joohnq.core.validator

import com.joohnq.core.exceptions.PasswordValidatorException

object PasswordValidator {
				operator fun invoke(password: String): Boolean {
								if (password.trim().isEmpty()) throw PasswordValidatorException.PasswordEmpty()
								if (password.length < 6) throw PasswordValidatorException.PasswordIsToShort()
								return true
				}
}