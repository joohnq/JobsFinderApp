package com.joohnq.jobsfinderapp.viewmodel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.joohnq.jobsfinderapp.model.entity.User
import com.joohnq.jobsfinderapp.model.repository.UserRepository
import com.joohnq.jobsfinderapp.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val repository: UserRepository,
) : ViewModel() {

    private val _user = MutableLiveData<UiState<User?>>()
    val user: LiveData<UiState<User?>>
        get() = _user

    fun getUserData() {
        _user.value = UiState.Loading
        repository.getUserData(){user ->
            _user.value = user
        }
    }

    fun updateUserToDatabase(user: User) {
        _user.value = UiState.Loading
        repository.updateUserToDatabase(user){userUpdated ->
            _user.value = userUpdated
        }
    }

    fun getUserFromDatabase(user: User) {
        _user.value = UiState.Loading
        repository.getUserFromDatabase(user){userGetter ->
            _user.value = userGetter
        }
    }

    fun registerUserToDatabaseWithGoogle(user: User) {
        _user.value = UiState.Loading
        repository.registerUserToDatabaseWithGoogle(user){userRegister ->
            _user.value = userRegister
        }
    }
    fun getUserUid(result: (String?) -> Unit) {
        repository.getUserUid{
            result(it)
        }
    }

    fun updateUserEmail(email: String, result: (UiState<String?>) -> Unit){
        _user.postValue(UiState.Loading)
        repository.updateUserEmail(email, result)
    }

    suspend fun updateUserImage(uri: Uri, result: (UiState<String?>) -> Unit){
        _user.postValue(UiState.Loading)
        repository.updateUserImage(uri, result)
    }
}