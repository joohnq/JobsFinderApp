package com.joohnq.user.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joohnq.user.data.repository.UserRepository
import com.joohnq.core.domain.entity.UiState
import com.joohnq.core.domain.entity.UiState.Companion.getDataOrNull
import com.joohnq.user.domain.entity.User
import com.joohnq.core.domain.exceptions.UserException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class UserViewModelState(
				val user: UiState<User> = UiState.None,
				val updating: UiState<String> = UiState.None
)

@HiltViewModel
class UserViewModel @Inject constructor(
				private val repository: UserRepository,
				private val dispatcher: CoroutineDispatcher,
): ViewModel() {
				private val _state = MutableStateFlow<UserViewModelState>(UserViewModelState())
				val state: StateFlow<UserViewModelState> = _state.asStateFlow()

				fun get() = viewModelScope.launch(dispatcher) {
								updateUserState(UiState.Loading)
								try {
												val user = repository.get()
												updateUserState(UiState.Success(user))
								} catch (e: Exception) {
												updateUserState(UiState.Failure(e.message))
								}
				}

				fun update(user: User) = viewModelScope.launch(dispatcher) {
								updateUpdatingState(UiState.Loading)
								try {
												val res = repository.update(user)
												updateUpdatingState(UiState.Success(res))
												get()
								} catch (e: Exception) {
												updateUpdatingState(UiState.Failure(e.message))
								}
				}

				fun updateUserOccupation(occupation: String) = viewModelScope.launch(dispatcher) {
								updateUpdatingState(UiState.Loading)
								try {
												val id = getUserId()

												val res = repository.update(id, mapOf("occupation" to occupation))

												updateUpdatingState(UiState.Success(res))
												get()
								} catch (e: Exception) {
												updateUpdatingState(UiState.Failure(e.message))
								}
				}

				private fun resetUserState() {
								_state.update { it.copy(user = UiState.None) }
				}

				fun getUserId(): String =
								_state.value.user.getDataOrNull()?.id
												?: throw UserException.UserIdIsNull

				fun updateUserState(user: UiState<User>) {
								_state.update { it.copy(user = user) }
				}

				fun updateUpdatingState(state: UiState<String>) {
								_state.update { it.copy(updating = state) }
				}
}