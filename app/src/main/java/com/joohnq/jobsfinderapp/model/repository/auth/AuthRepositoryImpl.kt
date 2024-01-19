package com.joohnq.jobsfinderapp.model.repository.auth

import android.util.Log
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.firestore.FirebaseFirestore
import com.joohnq.jobsfinderapp.model.entity.User
import com.joohnq.jobsfinderapp.util.FireStoreCollection
import com.joohnq.jobsfinderapp.util.UiState
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val db: FirebaseFirestore,
    private val oneTapClient: SignInClient,
) : AuthRepository {

    override fun registerUser(user: User, password: String, result: (UiState<String>) -> Unit) {
        try {
            auth
                .createUserWithEmailAndPassword(user.email, password)
                .addOnSuccessListener { authResult ->
                    val userId = authResult.user?.uid
                    userId?.run {
                        user.id = userId
                        updateUserToDatabase(user) { state ->
                            when (state) {
                                is UiState.Success -> {
                                    result.invoke(UiState.Success("User register successfully!"))
                                }
                                is UiState.Failure -> {
                                    result.invoke(UiState.Failure(state.error))
                                }
                                else -> {}
                            }

                        }
                    }
                }.addOnFailureListener {
                    result.invoke(UiState.Failure(it.message.toString()))
                    Log.e("RegisterUser - AuthFailure", it.message.toString())
                }
        } catch (e: FirebaseAuthWeakPasswordException) {
            result.invoke(UiState.Failure("Authentication failed, Password should be at least 6 characters"))
        } catch (e: FirebaseAuthInvalidCredentialsException) {
            result.invoke(UiState.Failure("Authentication failed, Invalid email entered"))
        } catch (e: FirebaseAuthUserCollisionException) {
            result.invoke(UiState.Failure("Authentication failed, Email already registered."))
        } catch (e: Exception) {
            result.invoke(UiState.Failure(e.message.toString()))
            Log.e("RegisterUser - TryCatch", e.message.toString())
        }
    }

    override fun updateUserToDatabase(user: User, result: (UiState<String>) -> Unit) {
        try {
            user.id?.let { id ->
                db
                    .collection(FireStoreCollection.USER)
                    .document(id)
                    .set(user)
                    .addOnSuccessListener {
                        result.invoke(
                            UiState.Success("Success")
                        )
                    }
                    .addOnFailureListener {
                        result.invoke(UiState.Failure("Error"))
                        Log.e("RegisterUser - DbFailure", it.message.toString())
                    }
            }
        }catch (e: Exception){
            result.invoke(UiState.Failure(e.message.toString()))
        }
    }

    override fun getUserUid(result: (String?) -> Unit) {
        auth.currentUser?.let {
            result.invoke(it.uid)
        }
    }

    override fun getUserFromDatabase(result: (User?) -> Unit) {
        val userUid = auth.currentUser?.uid

        userUid?.let { id ->
            db.collection(FireStoreCollection.USER).document(id)
                .get()
                .addOnCompleteListener {
                    val user = it.result.toObject(User::class.java)
                    result.invoke(user)
                }
                .addOnFailureListener {
                    result.invoke(null)
                }
        }
    }

    override fun loginUser(email: String, password: String, result: (UiState<String>) -> Unit) {
        try {
            auth
                .signInWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    result.invoke(UiState.Success("Login Successfully"))
                }
                .addOnFailureListener {
                    result.invoke(UiState.Failure(it.message.toString()))
                    Log.e("LoginUser - Auth", it.message.toString())
                }
        } catch (e: FirebaseAuthInvalidCredentialsException) {
            result.invoke(UiState.Failure("Authentication failed, Invalid email entered"))
        } catch (e: FirebaseAuthUserCollisionException) {
            result.invoke(UiState.Failure("Authentication failed, Email already registered."))
        } catch (e: Exception) {
            result.invoke(UiState.Failure(e.message))
            Log.e("LoginUser - TryCatch", e.message.toString())
        }
    }

    override suspend fun logout(result: () -> Unit) {
        oneTapClient.signOut().await()
        auth.signOut()
        result.invoke()
    }
}