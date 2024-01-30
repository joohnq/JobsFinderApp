package com.joohnq.jobsfinderapp.viewmodel

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joohnq.jobsfinderapp.model.entity.Job
import com.joohnq.jobsfinderapp.model.entity.User
import com.joohnq.jobsfinderapp.model.repository.JobRepository
import com.joohnq.jobsfinderapp.model.repository.UserRepository
import com.joohnq.jobsfinderapp.sign_in.SignInResult
import com.joohnq.jobsfinderapp.util.Functions
import com.joohnq.jobsfinderapp.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val repository: UserRepository,
    private val jobRepository: JobRepository
) : ViewModel() {

    private val _user = MutableLiveData<UiState<User?>>()
    val user: LiveData<UiState<User?>>
        get() = _user

    private val _favorites = MutableLiveData<UiState<List<String>?>>()
    val favorites: LiveData<UiState<List<String>?>>
        get() = _favorites

    private val _favoritesDetails = MutableLiveData<UiState<MutableList<Job>?>>()
    val favoritesDetails: LiveData<UiState<MutableList<Job>?>>
        get() = _favoritesDetails

    fun setFavoritesDetails(result: UiState<MutableList<Job>>) {
        _favoritesDetails.value = result
    }

    fun handleJobIdFavorite(jobId: String) {
        _favorites.value = UiState.Loading
        repository.handleJobIdFavorite(
            jobId,
        ) { state ->
            Functions.handleUiState(
                state,
                onFailure = { error ->
                    _favorites.value = UiState.Failure(error)
                },
                onSuccess = {
                    _favorites.value = UiState.Success(it)
                },
                onLoading = {
                    _favorites.value = UiState.Loading
                }
            )
        }
    }

    fun getUserFromDatabase() {
        _user.value = UiState.Loading
        repository.getUserFromDatabase { state ->
            _user.value = state
            Functions.handleUiState(
                state,
                onFailure = { error ->
                    _favorites.value = UiState.Failure(error)
                },
                onSuccess = { user ->
                    user?.run {
                        _favorites.value = UiState.Success(this.favourites)
                    }
                },
                onLoading = {
                    _favorites.value = UiState.Loading
                }
            )
        }
    }

    fun isItemFavorite(jobId: String): Boolean? {
        return _favorites.value?.run {
            when (this) {
                is UiState.Success -> {
                    data?.contains(jobId) ?: false
                }

                else -> {
                    false
                }
            }
        }
    }

    fun updateUserToDatabase(user: User) {
        _user.value = UiState.Loading
        repository.updateUserToDatabase(user) {
            _user.value = it
        }
    }

    fun loginUserToDatabaseWithGoogle(signInResult: SignInResult, result: (User?) -> Unit) {
        _user.value = UiState.Loading
        if (signInResult.data != null) {
            repository.loginUserToDatabaseWithGoogle(signInResult.data) {
                _user.value = it
            }
            result(signInResult.data)
        } else {
            signInResult.errorMessage?.let { _user.value = UiState.Failure(it) }
        }
    }

    fun getUserUid(result: (String?) -> Unit) {
        repository.getUserUid {
            result(it)
        }
    }

    fun updateUserEmail(email: String, result: (UiState<String?>) -> Unit) {
        _user.postValue(UiState.Loading)
        repository.updateUserEmail(email, result)
    }

    suspend fun updateUserImage(uri: Uri, result: (UiState<String?>) -> Unit) {
        _user.postValue(UiState.Loading)
        repository.updateUserImage(uri, result)
    }
}