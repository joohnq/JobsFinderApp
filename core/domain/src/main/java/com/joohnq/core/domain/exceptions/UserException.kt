package com.joohnq.core.domain.exceptions

sealed class UserException(message: String): Exception(message) {
				data object UserNameEmpty: UserException("Username empty")
				data object UserIdIsNull: UserException("User ID is null")

				sealed class EmailException(message: String): Exception(message) {
								data object EmailInvalid: EmailException("Email invalid")
								data object EmailEmpty: EmailException("Email empty")
				}
				sealed class OccupationException(message: String): Exception(message) {
								data object OccupationEmpty: OccupationException("Occupation empty")
								data object OccupationIsNullOrEmpty: OccupationException("Occupation is null or empty")
				}
				sealed class PasswordException(message: String): Exception(message) {
								data object PasswordInvalid: PasswordException("Password invalid")
								data object PasswordEmpty: PasswordException("Password empty")
								data object PasswordIsToShort: PasswordException("Password is too short")
				}
}
