package com.joohnq.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joohhq.main.ui.state.HomeViewModelState
import com.joohnq.job.data.repository.JobRepository
import com.joohnq.job.domain.entity.Job
import com.joohnq.core.domain.entity.UiState
import com.joohnq.core.domain.exceptions.UserException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
				private val repository: JobRepository,
				private val dispatcher: CoroutineDispatcher
): ViewModel() {
				private val _state = MutableStateFlow<com.joohhq.main.ui.state.HomeViewModelState>(com.joohhq.main.ui.state.HomeViewModelState())
				val state: MutableStateFlow<com.joohhq.main.ui.state.HomeViewModelState> get() = _state

				fun getHomeJobs(occupation: String?) =
								viewModelScope.launch(dispatcher) {
												updateJobsState(UiState.Loading)
												try {
																if (occupation.isNullOrEmpty()) throw UserException.OccupationException.OccupationIsNullOrEmpty
																val jobs = repository.getJobsBySearch(occupation, 20)
																updateJobsState(UiState.Success(jobs))
												} catch (e: Exception) {
																updateJobsState(UiState.Failure(e.message))
												}
								}

				private fun updateJobsState(state: UiState<List<Job>>) {
								_state.update { it.copy(jobs = state) }
				}
}
