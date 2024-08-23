package com.joohnq.core.exceptions

sealed class FirebaseException(message: String): Exception(message) {
				class UserDocumentDoesNotExist: FirebaseException("User document does not exist")
				class JobsAlreadyApplied: FirebaseException("Jobs already applied")
				class UserIdIsNull: FirebaseException("User id is null")
				class ErrorOnAddFavorite: FirebaseException("Error on add favorite")
				class ErrorOnRemoveFavorite: FirebaseException("Error on remove favorite")
				class ErrorOnUpdateUser: FirebaseException("Error on update user")
				class ErrorOnUpdateUserImage: FirebaseException("Error on update user image")
				class ErrorOnUpdateUserFile: FirebaseException("Error on update user file")
				class ErrorOnLogin: FirebaseException("Error on login")
				class ErrorOnCreateUserInDatabase: FirebaseException("Error on create user in database")
				class UrlIsNull: FirebaseException("URL is null")
				class ErrorOnUpdateUserOccupation: FirebaseException("Error on update user occupation")
				class ErrorOnFetchFavorites: FirebaseException("Error on fetch user's favorites")
}