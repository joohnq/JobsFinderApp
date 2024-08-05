package com.joohnq.core.exceptions

sealed class PasswordValidatorException(message: String): Exception(message) {
				class PasswordInvalid: PasswordValidatorException("Password invalid")
				class PasswordEmpty: PasswordValidatorException("Password empty")
				class PasswordIsToShort: PasswordValidatorException("Password is too short")
}
