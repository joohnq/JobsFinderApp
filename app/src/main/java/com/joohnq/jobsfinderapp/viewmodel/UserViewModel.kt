package com.joohnq.jobsfinderapp.viewmodel

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.joohnq.jobsfinderapp.model.entity.Job
import com.joohnq.jobsfinderapp.model.entity.User
import com.joohnq.jobsfinderapp.model.repository.job.JobRepository
import com.joohnq.jobsfinderapp.model.repository.user.UserRepository
import com.joohnq.jobsfinderapp.util.Functions
import com.joohnq.jobsfinderapp.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
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

    private val _favoritesDetails = MutableLiveData<UiState<MutableList<Job>>>()
    val favoritesDetails: LiveData<UiState<MutableList<Job>>> get() = _favoritesDetails

    fun getJobsByFavorite(jobId: String) {
        _favoritesDetails.value = UiState.Loading
        jobRepository.getJobDetailsById(jobId) { job ->
            _favoritesDetails.value.run {
                Functions.handleUiState(
                    this,
                    onFailure = { error ->
                        _favoritesDetails.value = UiState.Failure(error)
                    },
                    onSuccess = { jobs ->
                        jobs.add(job)
                    }
                )
            }
        }
    }

    fun handleJobIdFavorite(jobId: String) {
        _favorites.value = UiState.Loading
        repository.handleJobIdFavorite(
            jobId,
            onHas = {
            },
            onDontHas = {
            },
            result = {
                _favorites.value = it
                Log.i("handleJobIdFavorite", it.toString())
            }
        )
    }

    fun isItemFavorite(jobId: String): Boolean? {
        return _favorites.value?.run {
            when (this) {
                is UiState.Success -> {
                    this.data?.contains(jobId) ?: false
                }

                is UiState.Failure -> {
                    Log.e("UserViewModel", "isItemFavorite: ${this.error}")
                    false
                }

                is UiState.Loading -> {
                    false
                }
            }
        }
    }

    fun getUserFavorites() {
        _favorites.value = UiState.Loading
        repository.getUserFavorites {
            _favorites.value = it
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

    fun updateUserToDatabase(user: User) {
        _user.value = UiState.Loading
        repository.updateUserToDatabase(user) {
            _user.value = it
        }
    }

    fun loginUserToDatabaseWithGoogle(user: User) {
        _user.value = UiState.Loading
        repository.loginUserToDatabaseWithGoogle(user) {
            _user.value = it
        }
    }

    fun registerUserToDatabaseWithGoogle(user: User) {
        _user.value = UiState.Loading
        repository.registerUserToDatabaseWithGoogle(user) {
            _user.value = it
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