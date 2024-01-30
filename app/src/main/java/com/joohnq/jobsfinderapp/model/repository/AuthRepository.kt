package com.joohnq.jobsfinderapp.model.repository

import android.net.Uri
import android.util.Log
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.auth.userProfileChangeRequest
import com.joohnq.jobsfinderapp.model.entity.AuthType
import com.joohnq.jobsfinderapp.model.entity.User
import com.joohnq.jobsfinderapp.util.Functions
import com.joohnq.jobsfinderapp.util.UiState
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(
    private val auth: FirebaseAuth,
    private val userRepository: UserRepository,
    private val oneTapClient: SignInClient,
) {
    fun createUserWithEmailAndPassword(
        user: User,
        password: String,
        result: (UiState<String>) -> Unit
    ) {
        try {
            auth
                .createUserWithEmailAndPassword(user.email, password)
                .addOnSuccessListener { authResult ->
                    val userId = authResult.user?.uid
                    user.authType = AuthType.EMAIL_PASSWORD
                    userId?.run {
                        user.id = userId
                        userRepository.updateUserToDatabase(user) { state ->
                            Functions.handleUiState(
                                state,
                                onFailure = { error ->
                                    result.invoke(UiState.Failure(error))
                                },
                                onSuccess = {
                                    result.invoke(UiState.Success("Success"))
                                },
                                onLoading = {
                                    result.invoke(UiState.Loading)
                                }
                            )
                        }
                    }
                }.addOnFailureListener {
                    result.invoke(UiState.Failure(null))
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

    fun signInWithEmailAndPassword(
        email: String,
        password: String,
        result: (UiState<String>) -> Unit
    ) {
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

    suspend fun signOut() {
        oneTapClient.signOut().await()
        auth.signOut()
    }

    fun updateProfile(user: User, result: (UiState<String>) -> Unit) {
        val currentUser = auth.currentUser

        val profileUpdates = userProfileChangeRequest {
            displayName = user.name
            photoUri = Uri.parse(user.imageUrl)
        }

        currentUser?.run {
            updateProfile(profileUpdates)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        result.invoke(UiState.Success("Success"))
                    } else {
                        result.invoke(UiState.Failure("Error to update User profile. 2"))
                        Log.e("UpdateUser - Auth", task.exception?.message.toString())
                    }
                }
        }
    }
}