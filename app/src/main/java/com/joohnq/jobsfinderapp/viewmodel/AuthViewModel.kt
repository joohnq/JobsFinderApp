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
    private val userViewModel: UserViewModel
) : ViewModel() {

    private val _register = MutableLiveData<UiState<String>>()
    val register: LiveData<UiState<String>>
        get() = _register

    private val _login = MutableLiveData<UiState<String>>()
    val login: LiveData<UiState<String>>
        get() = _login

    fun onLoginResult(user: User?) {
        _login.value = UiState.Loading
        _login.value = if (user != null) {
            UiState.Success("Success")
        } else {
            UiState.Failure("Erro")
        }
    }

    fun onLoginResult(user: User?, string: String) {
        _register.value = UiState.Loading
        _register.value = if (user != null) {
            UiState.Success("Success")
        } else {
            UiState.Failure("Erro")
        }
    }

    fun signInWithEmailAndPassword(
        email: String,
        password: String
    ) {
        _login.value = UiState.Loading
        repository.signInWithEmailAndPassword(email, password) {
            _login.value = it
        }
    }

    fun createUserWithEmailAndPassword(
        user: User,
        password: String
    ) {
        _register.value = UiState.Loading
        repository.createUserWithEmailAndPassword(user, password) {
            _register.value = it
        }
    }

    suspend fun logout() {
        repository.signOut()
    }

    fun updateUser(user: User, result: (UiState<String>?) -> Unit) {
        repository.updateProfile(user, result)
    }
}