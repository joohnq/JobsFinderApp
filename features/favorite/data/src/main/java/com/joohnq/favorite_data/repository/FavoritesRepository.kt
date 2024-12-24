package com.joohnq.favorite_data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.getField
import com.joohnq.domain.constants.FirebaseConstants
import com.joohnq.domain.exceptions.FirebaseException
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class FavoritesRepository @Inject constructor(
				private val auth: FirebaseAuth,
				private val db: FirebaseFirestore,
) {
				private fun userUid(): String =
								auth.currentUser?.uid ?: throw com.joohnq.domain.exceptions.FirebaseException.UserIdIsNull()

				suspend fun add(id: String): Boolean = suspendCoroutine { continuation ->
								try {
												db
																.collection(com.joohnq.domain.constants.FirebaseConstants.FIREBASE_USER)
																.document(userUid())
																.update(com.joohnq.domain.constants.FirebaseConstants.FIREBASE_FAVORITES, FieldValue.arrayUnion(id))
																.addOnCompleteListener { task ->
																				if (!task.isSuccessful) throw com.joohnq.domain.exceptions.FirebaseException.ErrorOnAddFavorite()
																				continuation.resume(true)
																}
								} catch (e: Exception) {
												continuation.resumeWithException(e)
								}
				}

				suspend fun remove(id: String): Boolean = suspendCoroutine { continuation ->
								try {
												db
																.collection(com.joohnq.domain.constants.FirebaseConstants.FIREBASE_USER)
																.document(userUid())
																.update(
																				com.joohnq.domain.constants.FirebaseConstants.FIREBASE_FAVORITES,
																				FieldValue.arrayRemove(id)
																)
																.addOnCompleteListener { task ->
																				if (!task.isSuccessful) throw com.joohnq.domain.exceptions.FirebaseException.ErrorOnAddFavorite()
																				continuation.resume(true)
																}
								} catch (e: Exception) {
												continuation.resumeWithException(e)
								}
				}

				suspend fun fetch(): List<String> = suspendCoroutine { continuation ->
								try {
												db
																.collection(com.joohnq.domain.constants.FirebaseConstants.FIREBASE_USER)
																.document(userUid())
																.get()
																.addOnCompleteListener { task ->
																				if (!task.isSuccessful) throw com.joohnq.domain.exceptions.FirebaseException.ErrorOnGetUser()
																				val favorites = task.result.get(com.joohnq.domain.constants.FirebaseConstants.FIREBASE_FAVORITES) as? List<*>
																				continuation.resume(
																								favorites?.filterIsInstance<String>().orEmpty()
																				)
																}
								} catch (e: Exception) {
												continuation.resumeWithException(e)
								}
				}
}
