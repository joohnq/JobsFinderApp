package com.joohnq.favorite_data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.joohnq.core.constants.FirebaseConstants
import com.joohnq.core.exceptions.FirebaseException
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FavoritesRepository @Inject constructor(
				private val auth: FirebaseAuth,
				private val db: FirebaseFirestore,
) {
				suspend fun add(id: String): Boolean {
								return try {
												val userId = auth.currentUser?.uid ?: return false
												db
																.collection(FirebaseConstants.FIREBASE_USER)
																.document(userId)
																.update(FirebaseConstants.FIREBASE_FAVORITES, FieldValue.arrayUnion(id))
																.await()
												true
								} catch (e: Exception) {
												false
								}
				}

				suspend fun remove(id: String): Boolean {
								return try {
												val userId = auth.currentUser?.uid ?: return false
												db
																.collection(FirebaseConstants.FIREBASE_USER)
																.document(userId)
																.update(
																				FirebaseConstants.FIREBASE_FAVORITES,
																				FieldValue.arrayRemove(id)
																)
																.await()
												true
								} catch (e: Exception) {
												false
								}
				}

				suspend fun fetch(): List<String> {
								return try {
												val userId = auth.currentUser?.uid ?: throw FirebaseException.UserIdIsNull()
												val getTask = db
																.collection(FirebaseConstants.FIREBASE_USER)
																.document(userId)
																.get()
												val documentSnapshot = getTask.await()
												if (!documentSnapshot.exists()) throw FirebaseException.UserDocumentDoesNotExist()
												val favorites = documentSnapshot.get(FirebaseConstants.FIREBASE_FAVORITES) as? List<*>

												favorites?.filterIsInstance<String>().orEmpty()
								} catch (e: Exception) {
												throw e
								}
				}
}
