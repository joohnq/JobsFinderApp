package com.joohnq.core.domain.validator

import com.joohnq.core.domain.exceptions.UserException

object OccupationValidator {
				operator fun invoke(occupation: String): Boolean {
								if (occupation.trim().isEmpty()) throw UserException.OccupationException.OccupationEmpty
								return true
				}
}