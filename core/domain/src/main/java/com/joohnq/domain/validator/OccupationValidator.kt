package com.joohnq.domain.validator

import com.joohnq.domain.exceptions.OccupationException

object OccupationValidator {
				operator fun invoke(occupation: String): Boolean {
								if (occupation.trim().isEmpty()) throw OccupationException.OccupationEmpty()
								return true
				}
}