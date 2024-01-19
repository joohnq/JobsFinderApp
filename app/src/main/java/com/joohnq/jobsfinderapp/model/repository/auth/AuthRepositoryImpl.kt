package com.joohnq.jobsfinderapp.model.repository.auth

import android.provider.Settings.Global.getString
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.auth.GoogleAuthProvider
import com.joohnq.jobsfinderapp.R
import com.joohnq.jobsfinderapp.model.entity.User
import com.joohnq.jobsfinderapp.utils.UiState
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth
) : AuthRepository {

    override fun registerUser(user: User, password: String, result: (UiState<String>) -> Unit) {
        try {
            auth
                .createUserWithEmailAndPassword(user.email, password)
                .addOnSuccessListener {
                    val userId = it.user?.uid

                    if (userId == null) {
                        result.invoke(UiState.Failure("User register successful, but user id is null"))
                    } else {
                        user.id = userId
                        result.invoke(UiState.Success("User register successful"))
                    }

                }.addOnFailureListener {
                    result.invoke(UiState.Failure(it.message.toString()))
                }
        } catch (e: FirebaseAuthWeakPasswordException) {
            result.invoke(UiState.Failure("Authentication failed, Password should be at least 6 characters"))
        } catch (e: FirebaseAuthInvalidCredentialsException) {
            result.invoke(UiState.Failure("Authentication failed, Invalid email entered"))
        } catch (e: FirebaseAuthUserCollisionException) {
            result.invoke(UiState.Failure("Authentication failed, Email already registered."))
        } catch (e: Exception) {
            result.invoke(UiState.Failure(e.message))
        }
    }

    override fun loginUser(email: String, password: String, result: (UiState<String>) -> Unit) {
        try {
            auth
                .signInWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    val userId = it.user?.uid

                    if (userId == null) {
                        result.invoke(UiState.Failure("Login successful, but user id is null"))
                    } else {
                        result.invoke(UiState.Success("Login Successful"))
                    }

                }.addOnFailureListener {
                    result.invoke(UiState.Failure(it.message.toString()))
                }
        } catch (e: FirebaseAuthInvalidCredentialsException) {
            result.invoke(UiState.Failure("Authentication failed, Invalid email entered"))
        } catch (e: FirebaseAuthUserCollisionException) {
            result.invoke(UiState.Failure("Authentication failed, Email already registered."))
        } catch (e: Exception) {
            result.invoke(UiState.Failure(e.message))
        }
    }

    override fun logout(result: () -> Unit) {
        auth.signOut()
        result.invoke()
    }

    override fun getUserUid(result: (String?) -> Unit) {
        val userUid = auth.currentUser?.uid
        result.invoke(userUid)
    }
}