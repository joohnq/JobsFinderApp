package com.joohnq.core.domain.validator

import android.util.Patterns
import com.joohnq.core.domain.exceptions.UserException

object EmailValidator {
				operator fun invoke(email: String): Boolean {
								if (email.trim().isEmpty()) throw UserException.EmailException.EmailEmpty
								if (Patterns.EMAIL_ADDRESS.matcher(email).matches().not())
												throw UserException.EmailException.EmailInvalid
								return true
				}
}