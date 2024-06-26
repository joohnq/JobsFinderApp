package com.joohnq.jobsfinderapp.viewmodel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joohnq.jobsfinderapp.model.entity.Job
import com.joohnq.jobsfinderapp.model.entity.User
import com.joohnq.jobsfinderapp.model.repository.DatabaseRepository
import com.joohnq.jobsfinderapp.model.repository.StorageRepository
import com.joohnq.jobsfinderapp.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val storageRepository: StorageRepository,
    private val databaseRepository: DatabaseRepository,
    private val ioDispatcher: CoroutineDispatcher
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

    fun addJobFavorite(id: String) {
        _favorites.value = UiState.Loading
        viewModelScope.launch(ioDispatcher) {
            try {
                val res = databaseRepository.addJobFavorite(id)

                if (!res) throw Throwable("Error on add job to favorite")

                getJobsApplication()
            } catch (e: Exception) {
                _favorites.value = UiState.Failure(e.message)
            }
        }
    }

    fun removeJobFavorite(id: String) {
        _favorites.value = UiState.Loading
        viewModelScope.launch(ioDispatcher) {
            try {
                val res = databaseRepository.removeJobFavorite(id)

                if (!res) throw Throwable("Error on remove job from favorite")

                getJobsFavorites()
            } catch (e: Exception) {
                _favorites.value = UiState.Failure(e.message)
            }
        }
    }

    fun getJobsFavorites() {
        _favorites.value = UiState.Loading
        viewModelScope.launch(ioDispatcher) {
            try {
                val jobs = databaseRepository.getJobFavorites()

                _favorites.value = UiState.Success(jobs)
            } catch (e: Exception) {
                _favorites.value = UiState.Failure(e.message)
            }
        }
    }

    fun addJobApplication(id: String) {
        _applications.value = UiState.Loading
        viewModelScope.launch(ioDispatcher) {
            try {
                val res = databaseRepository.addJobApplication(id)

                if (!res) throw Throwable("Error on add job application")

                getJobsApplication()
            } catch (e: Exception) {
                _applications.value = UiState.Failure(e.message)
            }
        }
    }

    fun removeJobApplication(id: String) {
        _applications.value = UiState.Loading
        viewModelScope.launch(ioDispatcher) {
            try {
                val res = databaseRepository.removeJobApplication(id)

                if (!res) throw Throwable("Error on remove job application")

                getJobsApplication()
            } catch (e: Exception) {
                _applications.value = UiState.Failure(e.message)
            }
        }
    }

    fun getJobsApplication() {
        _applications.value = UiState.Loading
        viewModelScope.launch(ioDispatcher) {
            try {
                val jobs = databaseRepository.getJobApplication()

                _applications.value = UiState.Success(jobs)
            } catch (e: Exception) {
                _applications.value = UiState.Failure(e.message)
            }
        }
    }

    fun getUser() {
        _user.value = UiState.Loading
        viewModelScope.launch(ioDispatcher) {
            try {
                val user = databaseRepository.getUser()
                _user.postValue(UiState.Success(user))
            } catch (e: Exception) {
                _user.postValue(UiState.Failure(e.message))
            }
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

    fun updateUser(user: User) {
        _user.value = UiState.Loading
        viewModelScope.launch(ioDispatcher) {
            try {
                val res = databaseRepository.updateUser(user)

                if (!res) throw Throwable("Error on update user")

                getUser()
            } catch (e: Exception) {
                _user.value = UiState.Failure(e.message)
            }
        }
    }

//    fun loginUserToDatabaseWithGoogle(signInResult: SignInResult) {
//        _user.value = UiState.Loading
//        if (signInResult.data != null) {
//            repository.loginUserToDatabaseWithGoogle(signInResult.data) {
//                _user.value = it
//            }
//            result(signInResult.data)
//        } else {
//            signInResult.errorMessage?.let { _user.value = UiState.Failure(it) }
//        }
//    }

    fun updateUserImage(uri: Uri) {
        _user.postValue(UiState.Loading)
        viewModelScope.launch(ioDispatcher) {
            try {
                val url = storageRepository.updateUserImageReturningUrl(uri)
                val res = databaseRepository.updateUserImage(url)

                if (!res) throw Throwable("Error on update user image")

                getUser()
            } catch (e: Exception) {
                _user.postValue(UiState.Failure(e.message))
            }
        }
    }

    fun addUserFile(uri: Uri) {
        viewModelScope.launch(ioDispatcher) {
            try {
                val res = storageRepository.updateUserFile(uri)

                if (!res) throw Throwable("Error on update user file")
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun setUserNone() {
        _user.value = UiState.None
    }
}