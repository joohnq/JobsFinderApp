package com.joohnq.user.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joohnq.domain.exceptions.FirebaseException
import com.joohnq.ui.state.UiState
import com.joohnq.ui.state.getDataOrNull
import com.joohnq.data.repository.UserRepository
import com.joohnq.domain.entities.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class UserViewModelState(
				val user: com.joohnq.ui.state.UiState<User> = com.joohnq.ui.state.UiState.None,
				val updating: com.joohnq.ui.state.UiState<String> = com.joohnq.ui.state.UiState.None
)

@HiltViewModel
class UserViewModel @Inject constructor(
				private val repository: UserRepository,
				private val dispatcher: CoroutineDispatcher,
): ViewModel() {
				private val _state = MutableStateFlow<UserViewModelState>(UserViewModelState())
				val state: StateFlow<UserViewModelState> = _state.asStateFlow()

				fun get() = viewModelScope.launch(dispatcher) {
								updateUserState(com.joohnq.ui.state.UiState.Loading)
								try {
												val user = repository.get()
												updateUserState(com.joohnq.ui.state.UiState.Success(user))
								} catch (e: Exception) {
												updateUserState(com.joohnq.ui.state.UiState.Failure(e.message))
								}
				}

				fun update(user: User) = viewModelScope.launch(dispatcher) {
								updateUpdatingState(com.joohnq.ui.state.UiState.Loading)
								try {
												val res = repository.update(user)
												updateUpdatingState(com.joohnq.ui.state.UiState.Success(res))
												get()
								} catch (e: Exception) {
												updateUpdatingState(com.joohnq.ui.state.UiState.Failure(e.message))
								}
				}

				fun updateUserOccupation(occupation: String) = viewModelScope.launch(dispatcher) {
								updateUpdatingState(com.joohnq.ui.state.UiState.Loading)
								try {
												val id = getUserId()

												val res = repository.update(id, mapOf("occupation" to occupation))

												updateUpdatingState(com.joohnq.ui.state.UiState.Success(res))
												get()
								} catch (e: Exception) {
												updateUpdatingState(com.joohnq.ui.state.UiState.Failure(e.message))
								}
				}

				private fun resetUserState() {
								_state.update { it.copy(user = com.joohnq.ui.state.UiState.None) }
				}

				fun getUserId(): String =
								_state.value.user.getDataOrNull()?.id
												?: throw com.joohnq.domain.exceptions.FirebaseException.ErrorOnUpdateUserOccupation()

				fun updateUserState(user: com.joohnq.ui.state.UiState<User>) {
								_state.update { it.copy(user = user) }
				}

				fun updateUpdatingState(state: com.joohnq.ui.state.UiState<String>) {
								_state.update { it.copy(updating = state) }
				}
}