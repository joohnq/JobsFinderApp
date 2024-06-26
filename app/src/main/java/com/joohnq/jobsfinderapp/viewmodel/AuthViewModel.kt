package com.joohnq.jobsfinderapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joohnq.jobsfinderapp.model.entity.User
import com.joohnq.jobsfinderapp.model.repository.AuthRepository
import com.joohnq.jobsfinderapp.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val userViewModel: UserViewModel,
    private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {
    private val _register = MutableLiveData<UiState<String>>()
    val register: LiveData<UiState<String>>
        get() = _register

    private val _login = MutableLiveData<UiState<String>>()
    val login: LiveData<UiState<String>>
        get() = _login

//    fun onLoginResult(user: User?) {
//        _login.value = UiState.Loading
//        _login.value = if (user != null) UiState.Success("Success") else UiState.Failure("Erro")
//    }
//
//    fun onRegisterResult(user: User?) {
//        _register.value = UiState.Loading
//        _register.value = if (user != null) UiState.Success("Success") else UiState.Failure("Erro")
//    }

    fun signInWithEmailAndPassword(
        email: String,
        password: String
    ) {
        _login.value = UiState.Loading
        viewModelScope.launch(ioDispatcher) {
            try {
                val res = authRepository.signInWithEmailAndPassword(email, password)
                if (!res) {
                    _login.value = UiState.Failure("Error on login")
                }
                userViewModel.getUser()
            } catch (e: Exception) {
                _login.value = UiState.Failure(e.message.toString())
            }
        }
    }

    fun createUserWithEmailAndPassword(
        user: User,
        password: String
    ) {
        _register.value = UiState.Loading
        viewModelScope.launch(ioDispatcher) {
            try {
                val res = authRepository.createUserWithEmailAndPassword(user, password)

                if (!res) _register.value = UiState.Failure("Error on register")

                userViewModel.getUser()
            } catch (e: Exception) {
                _register.value = UiState.Failure(e.message.toString())
            }
        }
    }

    fun signOut() {
        viewModelScope.launch {
            authRepository.signOut()
            userViewModel.setUserNone()
        }
    }
}