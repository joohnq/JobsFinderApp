package com.joohnq.core.domain.validator

import com.joohnq.core.domain.exceptions.UserException

object UserNameValidator {
				operator fun invoke(username: String): Boolean {
								if (username.trim().isEmpty()) throw UserException.UserNameEmpty
								return true
				}
}