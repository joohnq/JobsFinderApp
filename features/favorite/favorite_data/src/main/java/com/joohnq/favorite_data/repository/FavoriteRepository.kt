package com.joohnq.favorite_data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.joohnq.core.contants.FirebaseConstants
import com.joohnq.core.exceptions.FirebaseException
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class FavoriteRepository @Inject constructor(
				private val auth: FirebaseAuth,
				private val db: FirebaseFirestore,
) {
				private fun userUid(): String =
								auth.currentUser?.uid ?: throw FirebaseException.UserIdIsNull()

				suspend fun add(id: String): Boolean = suspendCoroutine { continuation ->
								try {
												val userId = userUid()
												val userDocument = db.collection(FirebaseConstants.FIREBASE_USER).document(userId)

												userDocument
																.update(
																				FirebaseConstants.FIREBASE_FAVORITES,
																				FieldValue.arrayUnion(id)
																)
																.addOnSuccessListener {
																				continuation.resume(true)
																}
																.addOnFailureListener { continuation.resumeWithException(it) }
								} catch (e: Exception) {
												continuation.resumeWithException(e)
								}
				}

				suspend fun remove(id: String): Boolean = suspendCoroutine { continuation ->
								try {
												val userId = userUid()
												val userDocument = db.collection(FirebaseConstants.FIREBASE_USER).document(userId)

												userDocument
																.update(
																				FirebaseConstants.FIREBASE_FAVORITES,
																				FieldValue.arrayRemove(id)
																)
																.addOnSuccessListener {
																				continuation.resume(true)
																}
																.addOnFailureListener { continuation.resumeWithException(it) }
								} catch (e: Exception) {
												continuation.resumeWithException(e)
								}
				}

				suspend fun fetch(): List<String> = suspendCoroutine { continuation ->
								try {
												val userId = userUid()
												val userDocument = db.collection(FirebaseConstants.FIREBASE_USER).document(userId)

												userDocument
																.get()
																.addOnSuccessListener { documentSnapshot ->
																				if (!documentSnapshot.exists()) {
																								continuation.resumeWithException(FirebaseException.UserDocumentDoesNotExist())
																				}

																				val favorites = documentSnapshot.get(FirebaseConstants.FIREBASE_FAVORITES)
																				if (favorites is List<*>) {
																								val stringFavorites = favorites.filterIsInstance<String>()
																								continuation.resume(stringFavorites)
																				} else {
																								continuation.resume(emptyList())
																				}
																}
																.addOnFailureListener { continuation.resumeWithException(it) }
								} catch (e: Exception) {
												continuation.resumeWithException(e)
								}
				}
}