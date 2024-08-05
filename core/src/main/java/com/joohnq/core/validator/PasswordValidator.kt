package com.joohnq.core.validator

import android.util.Patterns
import com.joohnq.core.exceptions.PasswordValidatorException

object PasswordValidator {
				operator fun invoke(password: String): Boolean {
								if (password.isEmpty()) throw PasswordValidatorException.PasswordEmpty()
								if (password.isBlank()) throw PasswordValidatorException.PasswordInvalid()
								if(password.length < 6) throw PasswordValidatorException.PasswordIsToShort()
								return true
				}
}