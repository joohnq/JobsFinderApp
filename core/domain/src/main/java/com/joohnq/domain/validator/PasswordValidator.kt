package com.joohnq.domain.validator

import com.joohnq.domain.exceptions.PasswordValidatorException

object PasswordValidator {
				operator fun invoke(password: String): Boolean {
								if (password.trim().isEmpty()) throw PasswordValidatorException.PasswordEmpty()
								if (password.length < 6) throw PasswordValidatorException.PasswordIsToShort()
								return true
				}
}