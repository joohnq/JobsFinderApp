package com.joohnq.core.validator

import android.util.Patterns
import com.joohnq.core.exceptions.EmailValidatorException

object EmailValidator {
				operator fun invoke(email: String): Boolean {
								if (email.isEmpty()) throw EmailValidatorException.EmailEmpty()
								if (email.isBlank()) throw EmailValidatorException.EmailInvalid()
								if (Patterns.EMAIL_ADDRESS.matcher(email)
																.matches()
																.not()
								) throw EmailValidatorException.EmailInvalid()
								return true
				}
}