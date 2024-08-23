package com.joohnq.job_ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joohnq.core.state.UiState
import com.joohnq.job_data.JobsDatabaseRepository
import com.joohnq.job_domain.entities.Job
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class JobsViewModel @Inject constructor(
				private val jobsDatabaseRepository: JobsDatabaseRepository,
				private val ioDispatcher: CoroutineDispatcher
): ViewModel() {
				private val _remoteJobs = MutableLiveData<UiState<List<Job>>>()
				val remoteJobs: LiveData<UiState<List<Job>>>
								get() = _remoteJobs

				private val _fullTimeJobs = MutableLiveData<UiState<List<Job>>>()
				val fullTimeJobs: LiveData<UiState<List<Job>>> get() = _fullTimeJobs

				private val _partTimeJobs = MutableLiveData<UiState<List<Job>>>()
				val partTimeJobs: LiveData<UiState<List<Job>>> get() = _partTimeJobs

				fun getRemoteJobs() =
								viewModelScope.launch(ioDispatcher) {
												_remoteJobs.postValue(UiState.Loading)
												try {
																val jobs = jobsDatabaseRepository.getRemoteJobs()

																_remoteJobs.postValue(UiState.Success(jobs))
												} catch (e: Exception) {
																_remoteJobs.postValue(UiState.Failure(e.message))
												}
								}

				fun getFullTimeJobs() =
								viewModelScope.launch(ioDispatcher) {
												_fullTimeJobs.postValue(UiState.Loading)
												try {
																val jobs = jobsDatabaseRepository.getFullTimeJobs()

																_fullTimeJobs.postValue(UiState.Success(jobs))
												} catch (e: Exception) {
																_fullTimeJobs.postValue(UiState.Failure(e.message))
												}
								}

				fun getPartTimeJobs() =
								viewModelScope.launch(ioDispatcher) {
												_partTimeJobs.postValue(UiState.Loading)
												try {
																val jobs = jobsDatabaseRepository.getPartTimeJobs()

																_partTimeJobs.postValue(UiState.Success(jobs))
												} catch (e: Exception) {
																_partTimeJobs.postValue(UiState.Failure(e.message))
												}
								}
}
