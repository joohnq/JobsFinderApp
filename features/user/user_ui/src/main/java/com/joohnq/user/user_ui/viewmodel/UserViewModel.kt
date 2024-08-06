package com.joohnq.user.user_ui.viewmodel

import android.net.Uri
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
				private val _user = MutableLiveData<UiState<User?>>()
				val user: LiveData<UiState<User?>>
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

				fun updateUserImage(uri: Uri) =
								viewModelScope.launch(ioDispatcher) {
												_user.postValue(UiState.Loading)
												try {
																val res = userRepository.updateUserImageUrl(uri)
																val url = userRepository.fetchUserImageUrl()
																val res2 = userRepository.updateProfileImageUrl(url)

																if (!res || !res2) throw FirebaseException.ErrorOnUpdateUserImage()

																fetchUser()
												} catch (e: Exception) {
																_user.postValue(UiState.Failure(e.message))
												}
								}

				suspend fun addUserFile(uri: Uri): Boolean = suspendCoroutine{continuation ->
								viewModelScope.launch(ioDispatcher) {
												try {
																val res = userRepository.updateUserFile(uri)

																if (!res) throw FirebaseException.ErrorOnUpdateUserFile()

																continuation.resume(true)
												} catch (e: Exception) {
																continuation.resumeWithException(e)
												}
								}
				}

				fun setUserNone() {
								_user.setIfNewValue(UiState.None)
				}
}