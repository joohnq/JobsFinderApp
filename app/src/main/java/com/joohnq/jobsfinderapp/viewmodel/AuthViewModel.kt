package com.joohnq.jobsfinderapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.joohnq.jobsfinderapp.model.entity.User
import com.joohnq.jobsfinderapp.model.repository.AuthRepository
import com.joohnq.jobsfinderapp.sign_in.SignInResult
import com.joohnq.jobsfinderapp.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository: AuthRepository,
) : ViewModel() {

    private val _register = MutableLiveData<UiState<String>>()
    val register: LiveData<UiState<String>>
        get() = _register

    private val _login = MutableLiveData<UiState<String>>()
    val login: LiveData<UiState<String>>
        get() = _login

    fun onSignInResult(signInResult: SignInResult, result: (User?) -> Unit) {
        _register.value = UiState.Loading
        if (signInResult.data != null) {
            _register.value = UiState.Success("Success")
            result(signInResult.data)
        } else {
            signInResult.errorMessage?.let { _register.value = UiState.Failure(it) }
        }
    }

    fun onLoginResult(signInResult: SignInResult, result: (User?) -> Unit) {
        _login.value = UiState.Loading
        if (signInResult.data != null) {
            _login.value = UiState.Success("Success")
            result(signInResult.data)
        } else {
            signInResult.errorMessage?.let { _login.value = UiState.Failure(it) }
        }
    }

    fun loginUser(
        email: String,
        password: String
    ) {
        _login.value = UiState.Loading
        repository.loginUser(email, password) {
            _login.value = it
        }
    }

    fun registerUser(
        user: User,
        password: String
    ) {
        _register.value = UiState.Loading
        repository.registerUser(user, password) {
            _register.value = it
        }
    }

    suspend fun logout() {
        repository.logout()
    }

    fun updateUser(user: User, result: (UiState<String>?) -> Unit) {
        repository.updateUser(user, result)
    }
}