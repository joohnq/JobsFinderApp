package com.joohnq.jobsfinderapp.model.repository

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.joohnq.jobsfinderapp.model.entity.User
import com.joohnq.jobsfinderapp.util.Constants
import com.joohnq.jobsfinderapp.util.Constants.FIREBASE_APPLICATION
import com.joohnq.jobsfinderapp.util.Constants.FIREBASE_FAVORITES
import com.joohnq.jobsfinderapp.util.UiState
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class DatabaseRepository @Inject constructor(
    private val auth: FirebaseAuth,
    private val db: FirebaseFirestore,
) {
    fun userUid(): String? = auth.currentUser?.uid

    suspend fun updateProfile(user: User): Boolean = suspendCoroutine { continuation ->
        val updates: Map<String, String> = mapOf(
            "name" to user.name,
            "email" to user.email,
            "imageUrl" to user.imageUrl
        )

        try {
            val id = userUid() ?: throw Exception("User id is NULL")
            db
                .collection(Constants.FIREBASE_USER)
                .document(id)
                .update(updates)
                .addOnSuccessListener { continuation.resume(true) }
                .addOnFailureListener { continuation.resumeWithException(it) }
        } catch (e: Exception) {
            continuation.resumeWithException(e)
        }
    }

    suspend fun addJobFavorite(
        id: String,
    ): Boolean = suspendCoroutine { continuation ->
        try {
            val userId = userUid() ?: throw Exception("User id is NULL")
            val userDocument = db.collection(Constants.FIREBASE_USER).document(userId)

            userDocument
                .get()
                .addOnSuccessListener { documentSnapshot ->
                    if (!documentSnapshot.exists()) {
                        continuation.resumeWithException(Throwable("User document does not exist"))
                    }

                    val favorites =
                        documentSnapshot.get(FIREBASE_FAVORITES) as? List<String> ?: emptyList()

                    if (favorites.contains(id)) {
                        continuation.resumeWithException(Throwable("Job already favorited"))
                    }

                    userDocument
                        .update(FIREBASE_FAVORITES, FieldValue.arrayUnion(id))
                        .addOnSuccessListener { continuation.resume(true) }
                        .addOnFailureListener { continuation.resumeWithException(it) }
                }
                .addOnFailureListener { continuation.resumeWithException(it) }
        } catch (e: Exception) {
            continuation.resumeWithException(e)
        }
    }

    suspend fun removeJobFavorite(
        id: String,
    ): Boolean = suspendCoroutine { continuation ->
        try {
            val userId = userUid() ?: throw Exception("User id is NULL")
            val userDocument = db.collection(Constants.FIREBASE_USER).document(userId)

            userDocument
                .get()
                .addOnSuccessListener { documentSnapshot ->
                    if (!documentSnapshot.exists()) {
                        continuation.resumeWithException(Throwable("User document does not exist"))
                    }

                    val favorites =
                        documentSnapshot.get(FIREBASE_FAVORITES) as? List<String> ?: emptyList()

                    if (favorites.isEmpty() || !favorites.contains(id)) {
                        continuation.resumeWithException(Throwable("Job id does not id favorited"))
                    }

                    userDocument
                        .update(FIREBASE_FAVORITES, FieldValue.arrayRemove(id))
                        .addOnSuccessListener { continuation.resume(true) }
                        .addOnFailureListener { continuation.resumeWithException(it) }
                }
                .addOnFailureListener { continuation.resumeWithException(it) }
        } catch (e: Exception) {
            continuation.resumeWithException(e)
        }
    }

    suspend fun getJobFavorites(): List<String> = suspendCoroutine { continuation ->
        try {
            val userId = userUid() ?: throw Exception("User id is NULL")
            val userDocument = db.collection(Constants.FIREBASE_USER).document(userId)

            userDocument
                .get()
                .addOnSuccessListener { documentSnapshot ->
                    if (!documentSnapshot.exists()) {
                        continuation.resumeWithException(Throwable("User document does not exist"))
                    }

                    val favorites =
                        documentSnapshot.get(FIREBASE_FAVORITES) as? List<String> ?: emptyList()

                    continuation.resume(favorites)
                }
                .addOnFailureListener { continuation.resumeWithException(it) }
        } catch (e: Exception) {
            continuation.resumeWithException(e)
        }
    }

    suspend fun addJobApplication(
        id: String,
    ): Boolean = suspendCoroutine { continuation ->
        try {
            val userId = userUid() ?: throw Exception("User id is NULL")
            val userDocument = db.collection(Constants.FIREBASE_USER).document(userId)

            userDocument
                .get()
                .addOnSuccessListener { documentSnapshot ->
                    if (!documentSnapshot.exists()) {
                        continuation.resumeWithException(Throwable("User document does not exist"))
                    }

                    val applications =
                        documentSnapshot.get(FIREBASE_APPLICATION) as? List<String> ?: emptyList()

                    if (applications.contains(id)) {
                        continuation.resumeWithException(Throwable("You already applied for this job"))
                    }

                    userDocument
                        .update(FIREBASE_APPLICATION, FieldValue.arrayUnion(id))
                        .addOnSuccessListener { continuation.resume(true) }
                        .addOnFailureListener { continuation.resumeWithException(it) }
                }
                .addOnFailureListener { continuation.resumeWithException(it) }
        } catch (e: Exception) {
            continuation.resumeWithException(e)
        }
    }

    suspend fun removeJobApplication(
        id: String,
    ): Boolean = suspendCoroutine { continuation ->
        try {
            val userId = userUid() ?: throw Exception("User id is NULL")
            val userDocument = db.collection(Constants.FIREBASE_USER).document(userId)

            userDocument
                .get()
                .addOnSuccessListener { documentSnapshot ->
                    if (!documentSnapshot.exists()) {
                        continuation.resumeWithException(Throwable("User document does not exist"))
                    }

                    val applications =
                        documentSnapshot.get(FIREBASE_APPLICATION) as? List<String> ?: emptyList()

                    if (applications.contains(id)) {
                        continuation.resumeWithException(Throwable("You already applied for this job"))
                    }

                    userDocument
                        .update(FIREBASE_APPLICATION, FieldValue.arrayRemove(id))
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
            val userId = userUid() ?: throw Exception("User id is NULL")
            val userDocument = db.collection(Constants.FIREBASE_USER).document(userId)

            userDocument
                .get()
                .addOnSuccessListener { documentSnapshot ->
                    if (!documentSnapshot.exists()) {
                        continuation.resumeWithException(Throwable("User document does not exist"))
                    }

                    val applications =
                        documentSnapshot.get(FIREBASE_APPLICATION) as? List<String> ?: emptyList()

                    continuation.resume(applications)
                }
                .addOnFailureListener { continuation.resumeWithException(it) }
        } catch (e: Exception) {
            continuation.resumeWithException(e)
        }
    }

    suspend fun updateUser(
        user: User,
    ): Boolean = suspendCoroutine { continuation ->
        try {
            val id = userUid() ?: throw Exception("User id is NULL")
            db
                .collection(Constants.FIREBASE_USER)
                .document(id)
                .set(user)
                .addOnSuccessListener { continuation.resume(true) }
                .addOnFailureListener { continuation.resumeWithException(it) }
        } catch (e: Exception) {
            continuation.resumeWithException(e)
        }
    }

    suspend fun updateUserImage(
        url: String,
    ): Boolean = suspendCoroutine { continuation ->
        try {
            val updates = mapOf("imageUrl" to url)
            val id = userUid() ?: throw Exception("User id is NULL")
            db
                .collection(Constants.FIREBASE_USER)
                .document(id)
                .update(updates)
                .addOnSuccessListener { continuation.resume(true) }
                .addOnFailureListener { continuation.resumeWithException(it) }
        } catch (e: Exception) {
            continuation.resumeWithException(e)
        }
    }

    suspend fun getUser(): User = suspendCoroutine { continuation ->
        try {
            val id = userUid() ?: throw Exception("User id is NULL")
            db
                .collection(Constants.FIREBASE_USER)
                .document(id)
                .get()
                .addOnSuccessListener { snapshot ->
                    val user = snapshot.toObject(User::class.java)
                    if (user == null) {
                        continuation.resumeWithException(Throwable("User is NULL"))
                    }

                    continuation.resume(user!!)
                }
                .addOnFailureListener { continuation.resumeWithException(it) }
        } catch (e: Exception) {
            continuation.resumeWithException(e)
        }
    }

}