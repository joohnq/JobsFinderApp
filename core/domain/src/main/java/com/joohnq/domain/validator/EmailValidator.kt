package com.joohnq.domain.validator

import android.util.Patterns
import com.joohnq.domain.exceptions.EmailException

object EmailValidator {
				operator fun invoke(email: String): Boolean {
								if (email.trim().isEmpty()) throw EmailException.EmailEmpty()
								if (Patterns.EMAIL_ADDRESS.matcher(email).matches().not())
												throw EmailException.EmailInvalid()
								return true
				}
}