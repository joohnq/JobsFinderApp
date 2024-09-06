package com.joohnq.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joohnq.core.exceptions.FirebaseException
import com.joohnq.core.state.UiState
import com.joohnq.job_data.JobsDatabaseRepository
import com.joohnq.job_domain.entities.Job
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
				private val jobsDatabaseRepository: JobsDatabaseRepository,
				private val ioDispatcher: CoroutineDispatcher
): ViewModel() {
				private val _homeJobs = MutableLiveData<UiState<List<Job>>>()
				val homeJobs: LiveData<UiState<List<Job>>> get() = _homeJobs

				fun getHomeJobs(occupation: String?) =
								viewModelScope.launch(ioDispatcher) {
												_homeJobs.postValue(UiState.Loading)
												try {
																if (occupation.isNullOrEmpty()) throw FirebaseException.UserIdIsNull()
																val jobs = jobsDatabaseRepository.getJobsBySearch(occupation, 20)

																_homeJobs.postValue(UiState.Success(jobs))
												} catch (e: Exception) {
																_homeJobs.postValue(UiState.Failure(e.message))
												}
								}
}
