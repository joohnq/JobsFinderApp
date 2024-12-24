package com.joohnq.domain.exceptions

sealed class PasswordException(message: String): Exception(message) {
				class PasswordInvalid: PasswordException("Password invalid")
				class PasswordEmpty: PasswordException("Password empty")
				class PasswordIsToShort: PasswordException("Password is too short")
}
