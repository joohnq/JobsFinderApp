package com.joohnq.core.validator

import com.joohnq.core.exceptions.OccupationValidatorException

object OccupationValidator {
				operator fun invoke(occupation: String): Boolean {
								if (occupation.isEmpty()) throw OccupationValidatorException.OccupationEmpty()
								return true
				}
}