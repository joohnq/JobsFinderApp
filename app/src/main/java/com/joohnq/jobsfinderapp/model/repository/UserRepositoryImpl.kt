package com.joohnq.jobsfinderapp.model.repository

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.joohnq.jobsfinderapp.model.entity.User
import com.joohnq.jobsfinderapp.util.FireStoreCollection
import com.joohnq.jobsfinderapp.util.UiState
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val db: FirebaseFirestore,
) : UserRepository {

    override fun updateUserToDatabase(user: User, result: (UiState<User?>) -> Unit) {
        try {
            user.id?.let { id ->
                db
                    .collection(FireStoreCollection.USER)
                    .document(id)
                    .set(user)
                    .addOnSuccessListener {
                        result.invoke(
                            UiState.Success(user)
                        )
                    }
                    .addOnFailureListener {
                        result.invoke(UiState.Failure(null))
                        Log.e("RegisterUser - DbFailure", it.message.toString())
                    }
            }
        } catch (e: Exception) {
            result.invoke(UiState.Failure(e.message.toString()))
        }
    }

    override fun getUserUid(result: (String?) -> Unit) {
        result.invoke(auth.currentUser?.uid)
    }

    override fun getUserData(result: (UiState<User?>) -> Unit) {
        val userUid = auth.currentUser?.uid
        userUid?.let { id ->
            db.collection(FireStoreCollection.USER).document(id)
                .get()
                .addOnCompleteListener {
                    val user = it.result.toObject(User::class.java)
                    result.invoke(UiState.Success(user))
                }
                .addOnFailureListener {
                    result.invoke(UiState.Failure(null))
                }
        }
    }

}