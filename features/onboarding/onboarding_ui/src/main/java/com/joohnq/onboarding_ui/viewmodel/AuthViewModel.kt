package com.joohnq.onboarding_ui.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.AuthCredential
import com.joohnq.core.exceptions.FirebaseException
import com.joohnq.core.mappers.setIfNewValue
import com.joohnq.core.state.UiState
import com.joohnq.onboarding_data.repository.AuthRepository
import com.joohnq.onboarding_data.repository.GoogleAuthRepository
import com.joohnq.user.user_ui.viewmodel.UserViewModel
import com.joohnq.user_data.repository.UserRepository
import com.joohnq.user_domain.entities.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
				private val authRepository: AuthRepository,
				private val ioDispatcher: CoroutineDispatcher,
				private val googleAuthRepository: GoogleAuthRepository,
				private val userRepository: UserRepository,
): ViewModel() {
				private val _auth = MutableLiveData<UiState<String>>()
				val auth: LiveData<UiState<String>>
								get() = _auth

				private val _googleSignIn = MutableLiveData<UiState<String>>()
				val googleSignIn: LiveData<UiState<String>>
								get() = _googleSignIn

				private val _resendCountdown = MutableStateFlow(60)
				val resendCountdown: StateFlow<Int>
								get() = _resendCountdown

				private val _state = MutableLiveData<UiState<String>>()
				val state: LiveData<UiState<String>>
								get() = _state

				fun sendPasswordResetEmail(email: String) = viewModelScope.launch(ioDispatcher) {
								_resendCountdown.value = 60
								_state.postValue(UiState.Loading)
								try {
												userRepository.verifyIfEmailExists(email)
												authRepository.sendPasswordResetEmail(email)
												_state.postValue(UiState.Success(""))
								} catch (e: Exception) {
												_state.postValue(UiState.Failure(e.message))
								}
				}

				fun signInWithEmailAndPassword(
								email: String,
								password: String
				) {
								viewModelScope.launch(ioDispatcher) {
												_auth.setIfNewValue(UiState.Loading)
												try {
																val res = authRepository.signInWithEmailAndPassword(
																				email,
																				password
																)
																if (!res) throw FirebaseException.ErrorOnLogin()
																_auth.postValue(UiState.Success(""))
												} catch (e: Exception) {
																_auth.postValue(UiState.Failure(e.message.toString()))
												}
								}
				}

				fun createUserWithEmailAndPassword(
								user: User,
								password: String
				) {
								viewModelScope.launch(ioDispatcher) {
												_auth.postValue(UiState.Loading)
												try {
																val newUser = authRepository.createUserWithEmailAndPassword(
																				user,
																				password
																)

																val res = userRepository.updateUser(newUser)

																if (!res) throw FirebaseException.ErrorOnCreateUserInDatabase()

																signInWithEmailAndPassword(user.email, password)
												} catch (e: Exception) {
																_auth.postValue(UiState.Failure(e.message.toString()))
												}
								}
				}

				fun signInWithGoogleCredentials(context: Context) {
								viewModelScope.launch {
												_googleSignIn.postValue(UiState.Loading)
												try {
																val firebaseCredential: AuthCredential =
																				googleAuthRepository.getFirebaseCredential(context)
																val user = googleAuthRepository.signInWithGoogle(firebaseCredential)
																val res = userRepository.updateUser(user)

																if (!res) throw FirebaseException.ErrorOnLogin()

																_googleSignIn.postValue(UiState.Success("Success"))
												} catch (e: Exception) {
																_googleSignIn.postValue(UiState.Failure(e.message))
												}
								}
				}
}