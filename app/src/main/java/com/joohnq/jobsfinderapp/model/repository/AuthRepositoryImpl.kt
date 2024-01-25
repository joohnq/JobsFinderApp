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
import com.joohnq.jobsfinderapp.util.UiState
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val userRepository: UserRepository,
    private val oneTapClient: SignInClient,
) : AuthRepository {
    override fun registerUser(user: User, password: String, result: (UiState<String>) -> Unit) {
        try {
            auth
                .createUserWithEmailAndPassword(user.email, password)
                .addOnSuccessListener { authResult ->
                    val userId = authResult.user?.uid
                    user.authType = AuthType.EMAIL_PASSWORD
                    userId?.run {
                        user.id = userId
                        userRepository.updateUserToDatabase(user) { state ->
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

    override suspend fun logout() {
        oneTapClient.signOut().await()
        auth.signOut()
    }

    override fun updateUser(user: User, result: (UiState<String>) -> Unit) {
        val currentUser = auth.currentUser

        val profileUpdates = userProfileChangeRequest {
            displayName = user.name
            photoUri = Uri.parse(user.imageUrl)
        }

//
//        if(currentUser.email != user.email){
//            currentUser.updateEmail(user.email).addOnCompleteListener { task2 ->
//                result.invoke(
//                    if (task2.isSuccessful) {
//                        UiState.Success("User profile updated.2")
//                    } else {
//                        Log.e(
//                            "UpdateUser - UpdateEmail",
//                            task1.exception?.message.toString()
//                        )
//                        UiState.Failure("Error to update User profile.")
//                    }
//                )
//            }
//        }
//        UiState.Success("User profile updated.")
        currentUser!!.updateProfile(profileUpdates)
            .addOnCompleteListener { task1 ->
                if (task1.isSuccessful) {
                    result.invoke(UiState.Success("Success"))
                } else {
                    result.invoke(UiState.Failure("Error to update User profile. 2"))
                    Log.e("UpdateUser - Auth", task1.exception?.message.toString())
                }
            }
    }

    fun updateUserEmailOnAuth(email: String, result: (UiState<String?>) -> Unit) {
        try {
            val currentUser = auth.currentUser
            currentUser?.run {
                if (this.email != email) {
                    this.verifyBeforeUpdateEmail(email).addOnCompleteListener {
                        result.invoke(
                            if (it.isSuccessful)
                                UiState.Success("Success")
                            else UiState.Failure(it.exception?.message)
                        )
                    }
                }
            }
        } catch (e: Exception) {
            result.invoke(UiState.Failure(e.message.toString()))
        }
    }
}