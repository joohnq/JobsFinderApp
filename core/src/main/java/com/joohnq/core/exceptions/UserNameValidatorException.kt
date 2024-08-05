package com.joohnq.core.exceptions

sealed class UserNameValidatorException(message: String): Exception(message) {
				class UserNameEmpty: UserNameValidatorException("Username empty")
				class UserNameInvalid: UserNameValidatorException("Username invalid")
}
