package com.joohnq.core.exceptions

sealed class EmailValidatorException(message: String): Exception(message) {
				class EmailInvalid: EmailValidatorException("Email invalid")
				class EmailEmpty: EmailValidatorException("Email empty")
}
