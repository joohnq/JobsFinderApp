package com.joohnq.domain.exceptions

sealed class EmailValidatorException(message: String): Exception(message) {
				class EmailInvalid: EmailValidatorException("Email invalid")
				class EmailEmpty: EmailValidatorException("Email empty")
}
