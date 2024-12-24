package com.joohnq.core.domain.validator

import com.joohnq.core.domain.exceptions.UserException

object PasswordValidator {
				operator fun invoke(password: String): Boolean {
								if (password.trim().isEmpty()) throw UserException.PasswordException.PasswordEmpty
								if (password.length < 6) throw UserException.PasswordException.PasswordIsToShort
								return true
				}
}