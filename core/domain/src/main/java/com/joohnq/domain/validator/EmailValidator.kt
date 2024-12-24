package com.joohnq.domain.validator

import android.util.Patterns
import com.joohnq.domain.exceptions.EmailValidatorException

object EmailValidator {
				operator fun invoke(email: String): Boolean {
								if (email.trim().isEmpty()) throw EmailValidatorException.EmailEmpty()
								if (Patterns.EMAIL_ADDRESS.matcher(email).matches().not())
												throw EmailValidatorException.EmailInvalid()
								return true
				}
}