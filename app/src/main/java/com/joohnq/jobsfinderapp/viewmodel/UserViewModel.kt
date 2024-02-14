package com.joohnq.jobsfinderapp.viewmodel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.joohnq.jobsfinderapp.model.entity.Job
import com.joohnq.jobsfinderapp.model.entity.User
import com.joohnq.jobsfinderapp.model.repository.UserRepository
import com.joohnq.jobsfinderapp.sign_in.SignInResult
import com.joohnq.jobsfinderapp.util.Functions
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

    private val _favorites = MutableLiveData<UiState<List<String>?>>()
    val favorites: LiveData<UiState<List<String>?>>
        get() = _favorites

    private val _favoritesDetails = MutableLiveData<UiState<MutableList<Job>?>>()
    val favoritesDetails: LiveData<UiState<MutableList<Job>?>>
        get() = _favoritesDetails

    private val _applications = MutableLiveData<UiState<List<String>?>>()
    val applications: LiveData<UiState<List<String>?>>
        get() = _applications

    private val _applicationDetails = MutableLiveData<UiState<MutableList<Job>?>>()
    val applicationDetails: LiveData<UiState<MutableList<Job>?>>
        get() = _applicationDetails

    fun setFavoritesDetails(result: UiState<MutableList<Job>>) {
        _favoritesDetails.value = result
    }

    fun setApplicationDetails(result: UiState<MutableList<Job>>) {
        _applicationDetails.value = result
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

    fun handleJobIdApplication(jobId: String, result: (UiState<String>) -> Unit) {
        _applications.value = UiState.Loading
        repository.handleJobIdApplications(
            jobId,
        ) { state ->
            Functions.handleUiState(
                state,
                onFailure = { error ->
                    _applications.value = UiState.Failure(error)
                    result.invoke(UiState.Failure(error))
                },
                onSuccess = {
                    _applications.value = UiState.Success(it)
                    result.invoke(UiState.Success("Sucesso ao enviar a proposta"))
                },
                onLoading = {
                    _applications.value = UiState.Loading
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
                    _applications.value = UiState.Failure(error)
                },
                onSuccess = { user ->
                    user?.run {
                        _favorites.value = UiState.Success(this.favourites)
                        _applications.value = UiState.Success(this.application)
                    }
                },
                onLoading = {
                    _favorites.value = UiState.Loading
                    _applications.value = UiState.Loading
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

    fun isItemApplication(jobId: String): Boolean? {
        return _applications.value?.run {
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

    suspend fun addUserFile(uri: Uri, result: (UiState<String?>) -> Unit) {
        _user.postValue(UiState.Loading)
        repository.addUserImage(uri, result)
    }
}