package com.joohnq.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joohnq.data.repository.JobRepository
import com.joohnq.domain.entity.Job
import com.joohnq.domain.exceptions.OccupationException
import com.joohhq.ui.state.HomeViewModelState
import com.joohnq.domain.entity.UiState
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
				private val _state = MutableStateFlow<HomeViewModelState>(HomeViewModelState())
				val state: MutableStateFlow<HomeViewModelState> get() = _state

				fun getHomeJobs(occupation: String?) =
								viewModelScope.launch(dispatcher) {
												updateJobsState(UiState.Loading)
												try {
																if (occupation.isNullOrEmpty()) throw OccupationException.OccupationIsNullOrEmpty()
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
