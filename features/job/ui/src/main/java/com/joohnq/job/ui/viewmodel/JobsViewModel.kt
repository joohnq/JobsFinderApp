package com.joohnq.job.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joohnq.job.data.repository.JobRepository
import com.joohnq.job.domain.entity.Job
import com.joohnq.core.domain.entity.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class JobsState(
				val remote: UiState<List<Job>> = UiState.None,
				val fullTime: UiState<List<Job>> = UiState.None,
				val partTime: UiState<List<Job>> = UiState.None
)

@HiltViewModel
class JobsViewModel @Inject constructor(
				private val repository: JobRepository,
				private val dispatcher: CoroutineDispatcher
): ViewModel() {
				private val _state = MutableStateFlow<JobsState>(JobsState())
				val state: StateFlow<JobsState> get() = _state

				fun getRemoteJobs() =
								viewModelScope.launch(dispatcher) {
												updateRemoteState(UiState.Loading)
												try {
																val jobs = repository.getRemoteJobs()
																updateRemoteState(UiState.Success(jobs))
												} catch (e: Exception) {
																updateRemoteState(UiState.Failure(e.message))
												}
								}

				fun getFullTimeJobs() =
								viewModelScope.launch(dispatcher) {
												updateFullTimeState(UiState.Loading)
												try {
																val jobs = repository.getFullTimeJobs()
																updateFullTimeState(UiState.Success(jobs))
												} catch (e: Exception) {
																updateFullTimeState(UiState.Failure(e.message))
												}
								}

				fun getPartTimeJobs() =
								viewModelScope.launch(dispatcher) {
												updatePartTimeState(UiState.Loading)
												try {
																val jobs = repository.getPartTimeJobs()
																updatePartTimeState(UiState.Success(jobs))
												} catch (e: Exception) {
																updatePartTimeState(UiState.Failure(e.message))
												}
								}

				fun updateRemoteState(state: UiState<List<Job>>) {
								_state.update { it.copy(remote = state) }
				}

				fun updateFullTimeState(state: UiState<List<Job>>) {
								_state.update { it.copy(fullTime = state) }
				}

				fun updatePartTimeState(state: UiState<List<Job>>) {
								_state.update { it.copy(partTime = state) }
				}
}
