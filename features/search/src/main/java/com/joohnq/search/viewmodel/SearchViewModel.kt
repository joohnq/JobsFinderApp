package com.joohnq.search.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joohnq.core.state.UiState
import com.joohnq.core.state.getDataOrNull
import com.joohnq.job_data.JobsDatabaseRepository
import com.joohnq.job_domain.entities.Job
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
				private val repository: JobsDatabaseRepository,
				private val dispatcher: CoroutineDispatcher,
): ViewModel() {
				private val _jobsSearch = MutableLiveData<UiState<MutableList<Job>>>(UiState.None)
				val jobsSearch: LiveData<UiState<MutableList<Job>>>
								get() = _jobsSearch

				fun setJobsNone() = viewModelScope.launch { _jobsSearch.postValue(UiState.None) }

				fun searchJobs(text: String, limit: Long) =
								viewModelScope.launch(dispatcher) {
												_jobsSearch.postValue(UiState.Loading)
												try {
																val jobs = repository.getJobsBySearch(text, limit).toMutableList()
																_jobsSearch.postValue(UiState.Success(jobs))
												} catch (e: Exception) {
																_jobsSearch.postValue(UiState.Failure(e.message))
												}
								}

				fun searchJobsReload(text: String, offset: Long, limit: Long) =
								viewModelScope.launch(dispatcher) {
												val previousJobs = jobsSearch.value?.getDataOrNull()?.toMutableList() ?: mutableListOf()
												try {
																val newJobs = repository.getJobsBySearch(text, offset, limit)
																if (newJobs.isEmpty()) return@launch
																previousJobs.addAll(newJobs)
																_jobsSearch.postValue(UiState.Success(previousJobs))
												} catch (e: Exception) {
																_jobsSearch.postValue(UiState.Failure(e.message))
												}
								}

				fun setJobsSearchForTesting(jobs: MutableList<Job>) = viewModelScope.launch(dispatcher) {
								_jobsSearch.postValue(UiState.Success(jobs))
				}
}
