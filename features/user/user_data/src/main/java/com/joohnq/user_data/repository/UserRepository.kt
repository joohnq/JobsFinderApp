package com.joohnq.user_data.repository

import android.net.Uri
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.joohnq.core.constants.FirebaseConstants
import com.joohnq.core.exceptions.FirebaseException
import com.joohnq.user_domain.entities.User
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class UserRepository @Inject constructor(
				private val auth: FirebaseAuth,
				private val db: FirebaseFirestore,
				private val storage: FirebaseStorage,
) {
				fun userUid(): String =
								auth.currentUser?.uid ?: throw FirebaseException.UserIdIsNull()

				suspend fun updateUser(user: User): Boolean = suspendCoroutine { continuation ->
								try {
												db
																.collection(FirebaseConstants.FIREBASE_USER)
																.document(userUid())
																.set(user)
																.addOnCompleteListener { task ->
																				if (!task.isSuccessful)
																								throw task.exception ?: FirebaseException.ErrorOnUpdateUserImageUrl()

																				continuation.resume(true)
																}
								} catch (e: Exception) {
												continuation.resumeWithException(e)
								}
				}

				suspend fun updateUserImageUrl(url: String): Boolean = suspendCoroutine { continuation ->
								try {
												db
																.collection(FirebaseConstants.FIREBASE_USER)
																.document(userUid())
																.update(mapOf(FirebaseConstants.FIREBASE_IMAGE_URL to url))
																.addOnCompleteListener { task ->
																				if (!task.isSuccessful) throw task.exception
																								?: FirebaseException.ErrorOnUpdateUserImageUrl()

																				continuation.resume(true)
																}
								} catch (e: Exception) {
												continuation.resumeWithException(e)
								}
				}

				suspend fun fetchUser(): User = suspendCoroutine { continuation ->
								try {
												db
																.collection(FirebaseConstants.FIREBASE_USER)
																.document(userUid())
																.get()
																.addOnCompleteListener { task ->
																				if (!task.isSuccessful) throw task.exception
																								?: FirebaseException.ErrorOnGetUser()

																				val user = task.result.toObject(User::class.java)
																								?: throw FirebaseException.UserDocumentDoesNotExist()

																				continuation.resume(user)
																}
								} catch (e: Exception) {
												continuation.resumeWithException(e)
								}
				}

				suspend fun uploadUserImage(uri: Uri): Boolean = suspendCoroutine { continuation ->
								try {
												storage
																.getReference(FirebaseConstants.FIREBASE_USERS)
																.child(FirebaseConstants.FIREBASE_PHOTOS)
																.child(userUid())
																.putFile(uri)
																.addOnCompleteListener { taskUpload ->
																				if (!taskUpload.isSuccessful) throw taskUpload.exception
																								?: FirebaseException.ErrorOnUploadUserImage()

																				continuation.resume(true)
																}
								} catch (e: Exception) {
												continuation.resumeWithException(e)
								}
				}

				suspend fun updateUserOccupation(occupation: String): Boolean =
								suspendCoroutine { continuation ->
												try {
																val updates = mapOf("occupation" to occupation)
																db
																				.collection(FirebaseConstants.FIREBASE_USER)
																				.document(userUid())
																				.update(updates)
																				.addOnCompleteListener { task ->
																								if (!task.isSuccessful) throw task.exception
																												?: FirebaseException.ErrorOnUpdateUserImageUrl()

																								continuation.resume(true)
																				}
												} catch (e: Exception) {
																continuation.resumeWithException(e)
												}
								}

				suspend fun signOut(): Boolean = suspendCoroutine { continuation ->
								try {
												auth.signOut()
												continuation.resume(true)
								} catch (e: Exception) {
												continuation.resumeWithException(e)
								}
				}
}
