package com.joohnq.domain.validator

import com.joohnq.domain.exceptions.UserNameException

object UserNameValidator {
				operator fun invoke(username: String): Boolean {
								if (username.trim().isEmpty()) throw UserNameException.UserNameEmpty()
								return true
				}
}