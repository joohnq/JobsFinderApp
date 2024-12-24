package com.joohnq.domain.exceptions

sealed class OccupationException(message: String): Exception(message) {
				class OccupationEmpty: OccupationException("Occupation empty")
				class OccupationIsNullOrEmpty: OccupationException("Occupation is null or empty")
}
