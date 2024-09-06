package com.joohnq.user.user_ui.viewmodel

import  android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joohnq.core.exceptions.FirebaseException
import com.joohnq.core.mappers.setIfNewValue
import com.joohnq.core.state.UiState
import com.joohnq.user_data.repository.UserRepository
import com.joohnq.user_domain.entities.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

@HiltViewModel
class UserViewModel @Inject constructor(
				private val userRepository: UserRepository,
				private val ioDispatcher: CoroutineDispatcher,
): ViewModel() {
				private val _user = MutableLiveData<UiState<User>>()
				val user: LiveData<UiState<User>>
								get() = _user

				fun fetchUser() =
								viewModelScope.launch(ioDispatcher) {
												_user.setIfNewValue(UiState.Loading)
												try {
																val user = userRepository.fetchUser()

																_user.postValue(UiState.Success(user))
												} catch (e: Exception) {
																_user.postValue(UiState.Failure(e.message))
												}
								}

				fun updateUser(user: User) =
								viewModelScope.launch(ioDispatcher) {
												_user.postValue(UiState.Loading)
												try {
																val res = userRepository.updateUser(user)

																if (!res) throw FirebaseException.ErrorOnUpdateUser()

																fetchUser()
												} catch (e: Exception) {
																_user.postValue(UiState.Failure(e.message))
												}
								}

				fun updateUserImageUrl(uri: Uri) =
								viewModelScope.launch(ioDispatcher) {
												_user.postValue(UiState.Loading)
												try {
																val url = userRepository.uploadUserImage(uri)
																val res = userRepository.updateUserImageUrl(url)

																if (!res) throw FirebaseException.ErrorOnUpdateUserImage()

																fetchUser()
												} catch (e: Exception) {
																_user.postValue(UiState.Failure(e.message))
												}
								}

				fun updateUserOccupation(occupation: String) {
								viewModelScope.launch(ioDispatcher) {
												_user.postValue(UiState.Loading)
												try {
																val res = userRepository.updateUserOccupation(occupation)

																if (!res) throw FirebaseException.ErrorOnUpdateUserOccupation()

																fetchUser()
												} catch (e: Exception) {
																_user.postValue(UiState.Failure(e.message))
												}
								}
				}

				private fun setUserNone() {
								_user.setIfNewValue(UiState.None)
				}

				fun signOut() {
								viewModelScope.launch {
												userRepository.signOut()
												setUserNone()
								}
				}
}