package com.joohnq.domain.exceptions

sealed class UserNameException(message: String): Exception(message) {
				class UserNameEmpty: UserNameException("Username empty")
				class UserNameInvalid: UserNameException("Username invalid")
}
