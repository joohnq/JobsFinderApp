package com.joohnq.user.user_ui.viewmodel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joohnq.core.exceptions.FirebaseException
import com.joohnq.core.mappers.setIfNewValue
import com.joohnq.core.state.UiState
import com.joohnq.user_data.repository.UserRepositoryImpl
import com.joohnq.user_domain.entities.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
				private val userRepository: UserRepositoryImpl,
				private val dispatcher: CoroutineDispatcher,
): ViewModel() {
				private val _user = MutableStateFlow<UiState<User>>(UiState.None)
				val user: StateFlow<UiState<User>> = _user.asStateFlow()

				fun get() = viewModelScope.launch(dispatcher) {
								_user.update { UiState.Loading }
								try {
												val user = userRepository.getUser()
												_user.update { UiState.Success(user) }
								} catch (e: Exception) {
												_user.update { UiState.Failure(e.message) }
								}
				}

				fun update(user: User) = viewModelScope.launch(dispatcher) {
								_user.update { UiState.Loading }
								try {
												val res = userRepository.insertOrUpdate(user)

												if (!res) throw FirebaseException.ErrorOnUpdateUser()

												get()
								} catch (e: Exception) {
												_user.update { UiState.Failure(e.message) }
								}
				}

				fun updateUserImageUrl(uri: Uri) = viewModelScope.launch(dispatcher) {
								_user.update { UiState.Loading }
								try {
												val res = userRepository.uploadUserImage(uri)
												val url = userRepository.fetchUserImageUrl()
												val res2 = userRepository.updateUserImageUrl(url)
												if (!res || !res2) throw FirebaseException.ErrorOnUpdateUserImage()

												get()
								} catch (e: Exception) {
												_user.update { UiState.Failure(e.message) }
								}
				}

				fun updateUserOccupation(occupation: String) = viewModelScope.launch(dispatcher) {
								_user.update { UiState.Loading }
								try {
												val res = userRepository.updateUserOccupation(occupation)

												if (!res) throw FirebaseException.ErrorOnUpdateUserOccupation()

												get()
								} catch (e: Exception) {
												_user.update { UiState.Failure(e.message) }
								}
				}

				private fun setUserNone() {
								_user.update { UiState.None }
				}

				fun signOut() {
								viewModelScope.launch {
												userRepository.signOut()
												setUserNone()
								}
				}
}