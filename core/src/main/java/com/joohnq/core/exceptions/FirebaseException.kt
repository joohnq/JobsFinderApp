package com.joohnq.core.exceptions

sealed class FirebaseException(message: String): Exception(message) {
				class EmailDoesNotExist: FirebaseException("Email does not exist")
				class UserDocumentDoesNotExist: FirebaseException("User document does not exist")
				class JobsAlreadyApplied: FirebaseException("Jobs already applied")
				class ErrorOnUpdateUserImageUrl: FirebaseException("Error on update user image url")
				class ErrorOnFetchUserImageUrl: FirebaseException("Error on fetch user image url")
				class UserIdIsNull: FirebaseException("User id is null")
				class ErrorOnGetUser: FirebaseException("Error on get user")
				class ErrorOnUploadUserImage: FirebaseException("Error on upload user image")
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
				class ErrorOnSendPasswordResetEmail: FirebaseException("Error on send password reset email")
}