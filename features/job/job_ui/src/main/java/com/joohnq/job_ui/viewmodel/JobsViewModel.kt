package com.joohnq.job_ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joohnq.core.state.UiState
import com.joohnq.job_data.repository.JobRepository
import com.joohnq.job_domain.entities.Job
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class JobsViewModel @Inject constructor(
				private val jobRepository: JobRepository,
				private val ioDispatcher: CoroutineDispatcher
): ViewModel() {
				private val _popularJobs = MutableLiveData<UiState<List<Job>>>()
				val popularJobs: LiveData<UiState<List<Job>>> get() = _popularJobs
				private val _recentPostedJobs = MutableLiveData<UiState<List<Job>>>()
				val recentPostedJobs: LiveData<UiState<List<Job>>> get() = _recentPostedJobs

				init {
								getPopularJobs(1)
								getRecentPostedJobs(1)
				}

				fun getPopularJobs(page: Int) =
								viewModelScope.launch(ioDispatcher) {
												_popularJobs.postValue(UiState.Loading)
												try {
																jobRepository
																				.fetchAllPopularJobs(page * 10)
																				.catch { _popularJobs.postValue(UiState.Failure(it.message)) }
																				.collect { _popularJobs.postValue(UiState.Success(it)) }
												} catch (e: Exception) {
																_popularJobs.postValue(UiState.Failure(e.message))
												}
								}

				fun getRecentPostedJobs(page: Int) =
								viewModelScope.launch(ioDispatcher) {
												_recentPostedJobs.postValue(UiState.Loading)
												try {
																jobRepository
																				.fetchAllRecentPostedJobs(page * 10)
																				.catch { _recentPostedJobs.postValue(UiState.Failure(it.message)) }
																				.collect { _recentPostedJobs.postValue(UiState.Success(it)) }
												} catch (e: Exception) {
																_recentPostedJobs.postValue(UiState.Failure(e.message))
												}
								}
}
