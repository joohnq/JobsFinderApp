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
				private fun userUid(): String =
								auth.currentUser?.uid ?: throw FirebaseException.UserIdIsNull()

				suspend fun updateUserFile(uri: Uri): Boolean = suspendCoroutine { continuation ->
								try {
												val id = userUid()
												storage
																.getReference(FirebaseConstants.FIREBASE_USERS)
																.child(FirebaseConstants.FIREBASE_FILES)
																.child(id)
																.putFile(uri)
																.addOnSuccessListener { continuation.resume(true) }
																.addOnFailureListener { continuation.resumeWithException(it) }
								} catch (e: Exception) {
												continuation.resumeWithException(e)
								}
				}

				suspend fun updateUser(user: User): Boolean = suspendCoroutine { continuation ->
								try {
												val id = userUid()
												db
																.collection(FirebaseConstants.FIREBASE_USER)
																.document(id)
																.set(user)
																.addOnSuccessListener { continuation.resume(true) }
																.addOnFailureListener { continuation.resumeWithException(it) }
								} catch (e: Exception) {
												continuation.resumeWithException(e)
								}
				}

				suspend fun updateProfileImageUrl(url: String): Boolean = suspendCoroutine { continuation ->
								try {
												val updates = mapOf("imageUrl" to url)
												val id = userUid()
												db
																.collection(FirebaseConstants.FIREBASE_USER)
																.document(id)
																.update(updates)
																.addOnSuccessListener { continuation.resume(true) }
																.addOnFailureListener { continuation.resumeWithException(it) }
								} catch (e: Exception) {
												continuation.resumeWithException(e)
								}
				}

				suspend fun fetchUser(): User = suspendCoroutine { continuation ->
								try {
												val id = userUid()
												db
																.collection(FirebaseConstants.FIREBASE_USER)
																.document(id)
																.get()
																.addOnSuccessListener { snapshot ->
																				val user = snapshot.toObject(User::class.java)
																				if (user == null) {
																								continuation.resumeWithException(FirebaseException.UserDocumentDoesNotExist())
																				}

																				continuation.resume(user!!)
																}
																.addOnFailureListener { continuation.resumeWithException(it) }
								} catch (e: Exception) {
												continuation.resumeWithException(e)
								}
				}

				suspend fun fetchUserImageUrl(): String =
								suspendCoroutine { continuation ->
												try {
																val id = userUid()
																storage.getReference(FirebaseConstants.FIREBASE_USERS)
																				.child(FirebaseConstants.FIREBASE_PHOTOS)
																				.child(id)
																				.downloadUrl
																				.addOnSuccessListener { uri ->
																								uri?.run {
																												continuation.resume(uri.toString())
																								} ?: continuation.resumeWithException(FirebaseException.UrlIsNull())
																				}
																				.addOnFailureListener { continuation.resumeWithException(it) }
												} catch (e: Exception) {
																continuation.resumeWithException(e)
												}
								}

				suspend fun updateUserImageUrl(uri: Uri): Boolean = suspendCoroutine { continuation ->
								try {
												val id = userUid()
												storage
																.getReference(FirebaseConstants.FIREBASE_USERS)
																.child(FirebaseConstants.FIREBASE_PHOTOS)
																.child(id)
																.putFile(uri)
																.addOnSuccessListener { continuation.resume(true) }
																.addOnFailureListener { continuation.resumeWithException(it) }
								} catch (e: Exception) {
												continuation.resumeWithException(e)
								}
				}

				suspend fun updateUserOccupation(occupation: String): Boolean =
								suspendCoroutine { continuation ->
												try {
																val updates = mapOf("occupation" to occupation)
																val id = userUid()
																db
																				.collection(FirebaseConstants.FIREBASE_USER)
																				.document(id)
																				.update(updates)
																				.addOnSuccessListener { continuation.resume(true) }
																				.addOnFailureListener { continuation.resumeWithException(it) }
												} catch (e: Exception) {
																continuation.resumeWithException(e)
												}
								}
}
