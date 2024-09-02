package com.joohnq.core.exceptions

sealed class OccupationValidatorException(message: String): Exception(message) {
				class OccupationEmpty: OccupationValidatorException("Occupation empty")
}
