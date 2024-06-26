package com.joohnq.jobsfinderapp.model.repository

import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.firestore.FirebaseFirestore
import com.joohnq.jobsfinderapp.model.entity.User
import com.joohnq.jobsfinderapp.util.Constants
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton
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
    ): Boolean = suspendCoroutine { continuation ->
        try {
            auth
                .createUserWithEmailAndPassword(user.email, password)
                .addOnResultListener(continuation)
        } catch (e: Exception) {
            continuation.resumeWithException(e)
        }
    }

    suspend fun signInWithEmailAndPassword(
        email: String,
        password: String,
    ): Boolean = suspendCoroutine { continuation ->
        try {
            auth
                .signInWithEmailAndPassword(email, password)
                .addOnResultListener(continuation)
        } catch (e: FirebaseAuthInvalidCredentialsException) {
            continuation.resumeWithException(e)
        }
    }

    fun signOut(): Boolean {
        auth.signOut()
        return true
    }

    fun Task<AuthResult>.addOnResultListener(continuation: Continuation<Boolean>) {
        addOnSuccessListener { continuation.resume(true) }
            .addOnFailureListener { continuation.resumeWithException(it) }
    }
}