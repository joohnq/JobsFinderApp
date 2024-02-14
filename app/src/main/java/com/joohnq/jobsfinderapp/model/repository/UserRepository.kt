package com.joohnq.jobsfinderapp.model.repository

import android.net.Uri
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import com.joohnq.jobsfinderapp.model.entity.User
import com.joohnq.jobsfinderapp.util.Constants
import com.joohnq.jobsfinderapp.util.Functions
import com.joohnq.jobsfinderapp.util.UiState
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

@Singleton
class UserRepository @Inject constructor(
    private val auth: FirebaseAuth,
    private val db: FirebaseFirestore,
    private val storage: FirebaseStorage,
) {

    fun handleJobIdFavorite(
        jobId: String,
        result: (
            UiState<List<String>?>
        ) -> Unit
    ) {
        currentUser()?.run {
            val userDocument = db.collection(Constants.FIREBASE_USER).document(uid)

            userDocument.get().addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot.exists()) {
                    val favorites =
                        documentSnapshot.get("favourites") as? List<String> ?: emptyList()

                    if (favorites.contains(jobId)) {
                        userDocument.update("favourites", FieldValue.arrayRemove(jobId))
                            .addOnSuccessListener {
                                result.invoke(UiState.Success(favorites - jobId))
                                Log.e("removeJobFromFavorites", "Removed jobId: $jobId")
                            }
                            .addOnFailureListener { e ->
                                result.invoke(UiState.Failure(e.message.toString()))
                                Log.e("removeJobFromFavorites", e.message.toString())
                            }

                    } else {
                        userDocument.update("favourites", FieldValue.arrayUnion(jobId))
                            .addOnSuccessListener {
                                result.invoke(UiState.Success(favorites + jobId))
                                Log.e("addJobToFavorites", "Added jobId: $jobId")
                            }
                            .addOnFailureListener { e ->
                                result.invoke(UiState.Failure(e.message.toString()))
                                Log.e("addJobToFavorites", e.message.toString())
                            }
                    }
                } else {
                    result.invoke(UiState.Failure("User document does not exist"))
                    Log.e("handleJobIdFavorite", "User document does not exist")
                }
            }.addOnFailureListener { e ->
                result.invoke(UiState.Failure(e.message.toString()))
                Log.e("handleJobIdFavorite", e.message.toString())
            }
        }
    }

    fun handleJobIdApplications(
        jobId: String,
        result: (
            UiState<List<String>?>
        ) -> Unit
    ) {
        currentUser()?.run {
            val userDocument = db.collection(Constants.FIREBASE_USER).document(uid)

            userDocument.get().addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot.exists()) {
                    val applications =
                        documentSnapshot.get("application") as? List<String> ?: emptyList()

                    if (applications.contains(jobId)) {
                        userDocument.update("application", FieldValue.arrayRemove(jobId))
                            .addOnSuccessListener {
                                result.invoke(UiState.Success(applications - jobId))
                                Log.e("removeJobFromApplications", "Removed jobId: $jobId")
                            }
                            .addOnFailureListener { e ->
                                result.invoke(UiState.Failure(e.message.toString()))
                                Log.e("removeJobFromApplications", e.message.toString())
                            }

                    } else {
                        userDocument.update("application", FieldValue.arrayUnion(jobId))
                            .addOnSuccessListener {
                                result.invoke(UiState.Success(applications + jobId))
                                Log.e("addJobToApplications", "Added jobId: $jobId")
                            }
                            .addOnFailureListener { e ->
                                result.invoke(UiState.Failure(e.message.toString()))
                                Log.e("addJobToApplications", e.message.toString())
                            }
                    }
                } else {
                    result.invoke(UiState.Failure("User document does not exist"))
                    Log.e("handleJobIdApplications", "User document does not exist")
                }
            }.addOnFailureListener { e ->
                result.invoke(UiState.Failure(e.message.toString()))
                Log.e("handleJobIdApplications", e.message.toString())
            }
        }
    }

    fun getUserFavorites(result: (UiState<List<String>?>) -> Unit) {
        try {
            getUserFromDatabase { state ->
                Functions.handleUiState(
                    state,
                    onFailure = { error ->
                        result.invoke(UiState.Failure(error))
                    },
                    onSuccess = { user ->
                        user?.run {
                            result.invoke(UiState.Success(favourites))
                        }
                    },
                    onLoading = {
                        result.invoke(UiState.Loading)
                    }
                )
            }
        } catch (e: Exception) {
            result.invoke(UiState.Failure(e.message.toString()))
        }
    }

    private fun currentUser(): FirebaseUser? {
        return auth.currentUser
    }

    suspend fun updateUserImage(uri: Uri, result: (UiState<String?>) -> Unit) {
        try {
            currentUser()?.run {
                val uploadResult = try {
                    storage.getReference(Constants.FIREBASE_USERS)
                        .child(Constants.FIREBASE_PHOTOS)
                        .child(uid)
                        .putFile(uri)
                        .await()
                    getImageUrl(uid)
                } catch (e: Exception) {
                    UiState.Failure(e.message.toString())
                }

                result.invoke(uploadResult)
            }
        } catch (e: Exception) {
            result.invoke(UiState.Failure(e.message.toString()))
        }
    }

    suspend fun addUserImage(uri: Uri, result: (UiState<String?>) -> Unit) {
        try {
            currentUser()?.run {
                try {
                    storage.getReference(Constants.FIREBASE_USERS)
                        .child(Constants.FIREBASE_FILES)
                        .child(uid)
                        .putFile(uri)
                        .await()
                } catch (e: Exception) {
                    result.invoke(UiState.Failure(e.message.toString()))
                }
            }
        } catch (e: Exception) {
            result.invoke(UiState.Failure(e.message.toString()))
        }
    }

    private suspend fun StorageReference.putFileAwait(uri: Uri): UploadTask.TaskSnapshot {
        return suspendCoroutine { continuation ->
            putFile(uri)
                .addOnSuccessListener { task ->
                    continuation.resume(task)
                }
                .addOnFailureListener { exception ->
                    continuation.resumeWithException(exception)
                }
        }
    }

    private suspend fun getImageUrl(userId: String): UiState<String?> =
        suspendCoroutine { continuation ->
            try {
                storage.getReference(Constants.FIREBASE_USERS)
                    .child(Constants.FIREBASE_PHOTOS)
                    .child(userId)
                    .downloadUrl
                    .addOnSuccessListener { uri ->
                        continuation.resume(UiState.Success(uri.toString()))
                    }
                    .addOnFailureListener { exception ->
                        continuation.resume(UiState.Failure(exception.message.toString()))
                    }
            } catch (e: Exception) {
                continuation.resume(UiState.Failure(e.message.toString()))
            }
        }

    fun updateUserEmail(email: String, result: (UiState<String?>) -> Unit) {
        try {
            currentUser()?.run {
                verifyBeforeUpdateEmail(email)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            result.invoke(UiState.Success("Success"))
                        } else {
                            result.invoke(UiState.Failure("Error to update User profile. 1"))
                            Log.e("UpdateUser - Auth", task.exception?.message.toString())
                        }
                    }
            }
        } catch (e: Exception) {
            result.invoke(UiState.Failure(e.message.toString()))
            Log.e("UpdateUserEmail - TryCatch", e.message.toString())
        }
    }

    fun updateUserToDatabase(
        user: User,
        result: (UiState<User?>) -> Unit
    ) {
        try {
            currentUser()?.run {
                db
                    .collection(Constants.FIREBASE_USER)
                    .document(uid)
                    .set(user)
                    .addOnSuccessListener {
                        result.invoke(
                            UiState.Success(user)
                        )
                    }
                    .addOnFailureListener { error ->
                        result.invoke(UiState.Failure(null))
                        Log.e(
                            "UpdateUserToDatabase - DbFailure",
                            error.message.toString()
                        )
                    }
            }
        } catch (e: Exception) {
            result.invoke(UiState.Failure(e.message.toString()))
            Log.e("UpdateUserToDatabase - TryCatch", e.message.toString())
        }
    }

    fun getUserFromDatabase(
        result: (UiState<User?>) -> Unit
    ) {
        try {
            currentUser()?.uid?.run {
                db
                    .collection(Constants.FIREBASE_USER)
                    .document(this)
                    .get()
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            val userRes = it.result.toObject(User::class.java)
                            result.invoke(UiState.Success(userRes))
                        } else {
                            result.invoke(UiState.Failure(null))
                        }
                    }
            }
        } catch (e: Exception) {
            result.invoke(UiState.Failure(e.message.toString()))
            Log.e("UpdateUserToDatabase - TryCatch", e.message.toString())
        }
    }


    fun registerUserToDatabaseWithGoogle(
        user: User,
        result: (UiState<User?>) -> Unit
    ) {
        try {
            user.id?.let {
                db
                    .collection(Constants.FIREBASE_USER)
                    .document(it)
                    .set(user)
                    .addOnSuccessListener {
                        result.invoke(
                            UiState.Success(user)
                        )
                    }
                    .addOnFailureListener { error ->
                        result.invoke(UiState.Failure(null))
                        Log.e(
                            "UpdateUserToDatabase - DbFailure",
                            error.message.toString()
                        )
                    }
            }
        } catch (e: Exception) {
            result.invoke(UiState.Failure(e.message.toString()))
            Log.e("UpdateUserToDatabase - TryCatch", e.message.toString())
        }
    }

    fun loginUserToDatabaseWithGoogle(
        user: User,
        result: (UiState<User?>) -> Unit
    ) {
        user.id?.run {
            db
                .collection(Constants.FIREBASE_USER)
                .document(this)
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val document: DocumentSnapshot = task.result
                        if (document.exists()) {
                            val userDb = document.toObject(User::class.java)
                            result.invoke(UiState.Success(userDb))
                        } else {
                            registerUserToDatabaseWithGoogle(user, result)
                        }
                    } else {
                        result.invoke(UiState.Failure(task.exception?.message.toString()))
                    }
                }
        }

    }

    fun getUserUid(result: (String?) -> Unit) {
        result.invoke(auth.currentUser?.uid)
    }
}