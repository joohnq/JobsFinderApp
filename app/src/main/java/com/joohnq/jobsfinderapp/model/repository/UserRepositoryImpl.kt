package com.joohnq.jobsfinderapp.model.repository

import android.net.Uri
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import com.joohnq.jobsfinderapp.model.entity.User
import com.joohnq.jobsfinderapp.util.FireStoreCollection
import com.joohnq.jobsfinderapp.util.UiState
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

@Singleton
class UserRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val db: FirebaseFirestore,
    private val storage: FirebaseStorage,
) : UserRepository {

    override suspend fun updateUserImage(uri: Uri?, result: (UiState<String?>) -> Unit) {
        try {
            if (uri != null) {
                val currentUser = auth.currentUser
                currentUser?.run {
                    val uploadResult = try {
                        storage.getReference(FireStoreCollection.USERS)
                            .child(FireStoreCollection.PHOTOS)
                            .child(uid)
                            .putFile(uri)
                            .await()
                        uploadImageUrl(uid)
                    } catch (e: Exception) {
                        UiState.Failure(e.message.toString())
                    }

                    result.invoke(uploadResult)
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

    private suspend fun uploadImageUrl(userId: String): UiState<String?> = suspendCoroutine { continuation ->
        try {
            storage.getReference(FireStoreCollection.USERS)
                .child(FireStoreCollection.PHOTOS)
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

    override fun updateUserEmail(email: String, result: (UiState<String?>) -> Unit) {
        try {
            val currentUser = auth.currentUser
            currentUser?.run {
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

    override fun updateUserToDatabase(
        user: User,
        result: (UiState<User?>) -> Unit
    ) {
        try {
            val currentUser = auth.currentUser
            currentUser?.run {
                db
                    .collection(FireStoreCollection.USER)
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

    override fun getUserFromDatabase(
        user: User,
        result: (UiState<User?>) -> Unit
    ) {
        try {
            user.id?.run {
                db
                    .collection(FireStoreCollection.USER)
                    .document(this)
                    .get()
                    .addOnCompleteListener {
                        if (it.isSuccessful){
                            val userGetter = it.result.toObject(User::class.java)
                            result.invoke(UiState.Success(userGetter))
                        }else{
                            result.invoke(UiState.Failure(null))
                        }
                    }
            }
        } catch (e: Exception) {
            result.invoke(UiState.Failure(e.message.toString()))
            Log.e("UpdateUserToDatabase - TryCatch", e.message.toString())
        }
    }

    override fun registerUserToDatabaseWithGoogle(
        user: User,
        result: (UiState<User?>) -> Unit
    ) {
        try {
            user.id?.run {
                db
                    .collection(FireStoreCollection.USER)
                    .document(this)
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

    override fun getUserUid(result: (String?) -> Unit) {
        result.invoke(auth.currentUser?.uid)
    }

    override fun getUserData(result: (UiState<User?>) -> Unit) {
        val userUid = auth.currentUser?.uid
        userUid?.run {
            db.collection(FireStoreCollection.USER)
                .document(this)
                .get()
                .addOnCompleteListener {
                    if (it.isSuccessful){
                        val user = it.result.toObject(User::class.java)
                        result.invoke(UiState.Success(user))
                    }else{
                        result.invoke(UiState.Failure(null))
                    }
                }
        }
    }
}