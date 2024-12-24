package com.joohnq.domain.validator

import com.joohnq.domain.exceptions.PasswordException

object PasswordValidator {
				operator fun invoke(password: String): Boolean {
								if (password.trim().isEmpty()) throw PasswordException.PasswordEmpty()
								if (password.length < 6) throw PasswordException.PasswordIsToShort()
								return true
				}
}