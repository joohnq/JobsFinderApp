package com.joohnq.jobsfinderapp.model.repository

import android.net.Uri
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.joohnq.jobsfinderapp.util.Constants
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class StorageRepository @Inject constructor(
    private val auth: FirebaseAuth,
    private val storage: FirebaseStorage,
) {
    private fun userUid(): String =
        auth.currentUser?.uid ?: throw Throwable("User id is NULL")

    suspend fun updateUserImageReturningUrl(uri: Uri): String = suspendCoroutine { continuation ->
        try {
            val id = userUid()
            storage
                .getReference(Constants.FIREBASE_USERS)
                .child(Constants.FIREBASE_PHOTOS)
                .child(id)
                .putFile(uri)
                .addOnSuccessListener { snapshot ->
                    val url = snapshot.metadata?.reference?.downloadUrl?.result

                    if (url == null) continuation.resumeWithException(Throwable("Url is NULL"))

                    continuation.resume(url.toString())
                }
                .addOnFailureListener { continuation.resumeWithException(it) }
        } catch (e: Exception) {
            continuation.resumeWithException(e)
        }
    }

    private suspend fun getUserImage(): String =
        suspendCoroutine { continuation ->
            try {
                val id = userUid()
                storage.getReference(Constants.FIREBASE_USERS)
                    .child(Constants.FIREBASE_PHOTOS)
                    .child(id)
                    .downloadUrl
                    .addOnSuccessListener { uri ->
                        uri?.run {
                            continuation.resume(uri.toString())
                        } ?: continuation.resumeWithException(Throwable("Url is NULL"))
                    }
                    .addOnFailureListener { continuation.resumeWithException(it) }
            } catch (e: Exception) {
                continuation.resumeWithException(e)
            }
        }

    suspend fun updateUserFile(uri: Uri): Boolean = suspendCoroutine { continuation ->
        try {
            val id = userUid()
            storage
                .getReference(Constants.FIREBASE_USERS)
                .child(Constants.FIREBASE_FILES)
                .child(id)
                .putFile(uri)
                .addOnSuccessListener { continuation.resume(true) }
                .addOnFailureListener { continuation.resumeWithException(it) }
        } catch (e: Exception) {
            continuation.resumeWithException(e)
        }
    }
}