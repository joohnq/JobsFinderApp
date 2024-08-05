package com.joohnq.onboarding_data.repository

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.joohnq.core.exceptions.FirebaseException
import com.joohnq.onboarding_domain.entities.AuthType
import com.joohnq.user_domain.entities.User
import javax.inject.Inject
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class AuthRepository @Inject constructor(
				private val auth: FirebaseAuth,
) {
				suspend fun createUserWithEmailAndPassword(
								user: User,
								password: String,
				): User = suspendCoroutine { continuation ->
								try {
												auth.createUserWithEmailAndPassword(
																user.email,
																password
												)
																.addOnSuccessListener { snapshot ->
																				val uid = snapshot.user?.uid
																								?: throw FirebaseException.UserIdIsNull()
																				continuation.resume(
																								user.copy(
																												id = uid,
																												authType = AuthType.EMAIL_PASSWORD
																								)
																				)
																}
																.addOnFailureListener { continuation.resumeWithException(it) }
								} catch (e: Exception) {
												continuation.resumeWithException(e)
								}
				}

				suspend fun signInWithEmailAndPassword(
								email: String,
								password: String,
				): Boolean = suspendCoroutine { continuation ->
								try {
												auth.signInWithEmailAndPassword(
																email,
																password
												).addOnResultListener(continuation)
								} catch (e: Exception) {
												continuation.resumeWithException(e)
								}
				}

				fun signOut(): Boolean {
								auth.signOut()
								return true
				}

				private fun Task<AuthResult>.addOnResultListener(continuation: Continuation<Boolean>) {
								addOnSuccessListener { continuation.resume(true) }.addOnFailureListener {
												continuation.resumeWithException(
																it
												)
								}
				}
}