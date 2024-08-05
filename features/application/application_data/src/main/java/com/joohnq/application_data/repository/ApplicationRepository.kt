package com.joohnq.application_data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.joohnq.core.contants.FirebaseConstants
import com.joohnq.core.exceptions.FirebaseException
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class ApplicationRepository @Inject constructor(
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
																.get()
																.addOnSuccessListener { documentSnapshot ->
																				if (!documentSnapshot.exists()) {
																								continuation.resumeWithException(FirebaseException.UserDocumentDoesNotExist())
																				}

																				val applications =
																								documentSnapshot.get(FirebaseConstants.FIREBASE_APPLICATION)

																				if (applications !is List<*>) {
																								continuation.resume(false)
																								return@addOnSuccessListener
																				}

																				val stringApplications = applications.filterIsInstance<String>()

																				if (stringApplications.contains(id)) {
																								continuation.resumeWithException(FirebaseException.JobsAlreadyApplied())
																				}

																				userDocument
																								.update(
																												FirebaseConstants.FIREBASE_APPLICATION,
																												FieldValue.arrayUnion(id)
																								)
																								.addOnSuccessListener { continuation.resume(true) }
																								.addOnFailureListener { continuation.resumeWithException(it) }
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
																.get()
																.addOnSuccessListener { documentSnapshot ->
																				if (!documentSnapshot.exists()) {
																								continuation.resumeWithException(FirebaseException.UserDocumentDoesNotExist())
																				}

																				val applications =
																								documentSnapshot.get(FirebaseConstants.FIREBASE_APPLICATION)

																				if (applications !is List<*>) {
																								continuation.resume(false)
																								return@addOnSuccessListener
																				}

																				val stringApplications = applications.filterIsInstance<String>()

																				if (stringApplications.contains(id)) {
																								continuation.resumeWithException(FirebaseException.JobsAlreadyApplied())
																				}

																				userDocument
																								.update(
																												FirebaseConstants.FIREBASE_APPLICATION,
																												FieldValue.arrayRemove(id)
																								)
																								.addOnSuccessListener { continuation.resume(true) }
																								.addOnFailureListener { continuation.resumeWithException(it) }
																}
																.addOnFailureListener { continuation.resumeWithException(it) }
								} catch (e: Exception) {
												continuation.resumeWithException(e)
								}
				}

				suspend fun getJobApplication(): List<String> = suspendCoroutine { continuation ->
								try {
												val userId = userUid()
												val userDocument = db.collection(FirebaseConstants.FIREBASE_USER).document(userId)

												userDocument
																.get()
																.addOnSuccessListener { documentSnapshot ->
																				if (!documentSnapshot.exists()) {
																								continuation.resumeWithException(FirebaseException.UserDocumentDoesNotExist())
																				}

																				val applications =
																								documentSnapshot.get(FirebaseConstants.FIREBASE_APPLICATION)

																				if (applications !is List<*>) {
																								continuation.resume(emptyList())
																								return@addOnSuccessListener
																				}

																				val stringApplications = applications.filterIsInstance<String>()

																				continuation.resume(stringApplications)
																}
																.addOnFailureListener { continuation.resumeWithException(it) }
								} catch (e: Exception) {
												continuation.resumeWithException(e)
								}
				}
}
