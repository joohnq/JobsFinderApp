package com.joohnq.onboarding.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joohnq.auth.data.repository.AuthenticationRepository
import com.joohnq.auth.data.repository.TokenRepository
import com.joohnq.core.domain.entity.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class AuthState(
				val login: UiState<String> = UiState.None,
				val register: UiState<String> = UiState.None,
)

@HiltViewModel
class AuthViewModel @Inject constructor(
				private val repository: AuthenticationRepository,
				private val dispatcher: CoroutineDispatcher,
				private val tokenRepository: TokenRepository
): ViewModel() {
				private val _state = MutableStateFlow<AuthState>(AuthState())
				val state: StateFlow<AuthState> = _state.asStateFlow()

				fun signIn(
								email: String,
								password: String
				) {
								viewModelScope.launch(dispatcher) {
												_state.update { it.copy(login = UiState.Loading) }
												try {
																val res = repository.signIn(email, password)
																val isTokenSet = tokenRepository.set(res.token)
																if (!isTokenSet) throw Exception("Something went wrong saving token, try again later")
																_state.update { it.copy(login = UiState.Success("Successfully logged")) }
												} catch (e: Exception) {
																_state.update { it.copy(login = UiState.Failure(e.message.toString())) }
												}
								}
				}

				fun signUp(
								name: String,
								email: String,
								password: String
				) {
								viewModelScope.launch(dispatcher) {
												_state.update { it.copy(register = UiState.Loading) }
												try {
																repository.signUp(name, email, password)

																_state.update { it.copy(register = UiState.Success("Successfully registered, please login")) }
												} catch (e: Exception) {
																_state.update { it.copy(register = UiState.Failure(e.message.toString())) }
												}
								}
				}
}