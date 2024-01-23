package com.joohnq.jobsfinderapp.viewmodel

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

    fun getUserUid(result: (String?) -> Unit) {
        repository.getUserUid{
            result(it)
        }
    }
}