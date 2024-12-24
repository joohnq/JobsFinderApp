package com.joohnq.domain.exceptions

sealed class EmailException(message: String): Exception(message) {
				class EmailInvalid: EmailException("Email invalid")
				class EmailEmpty: EmailException("Email empty")
}
