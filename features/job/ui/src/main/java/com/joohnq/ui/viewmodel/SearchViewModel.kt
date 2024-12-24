package com.joohnq.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joohnq.data.repository.JobRepository
import com.joohnq.domain.entity.Job
import com.joohnq.domain.entity.UiState
import com.joohnq.domain.entity.UiState.Companion.getDataOrNull
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SearchViewModelState(
				val jobs: UiState<List<Job>> = UiState.None
)

@HiltViewModel
class SearchViewModel @Inject constructor(
				private val repository: JobRepository,
				private val dispatcher: CoroutineDispatcher,
): ViewModel() {
				private val _state = MutableStateFlow<SearchViewModelState>(SearchViewModelState())
				val state: MutableStateFlow<SearchViewModelState> = _state

				fun search(text: String, limit: Int) =
								viewModelScope.launch(dispatcher) {
												updateJobsState(UiState.Loading)
												try {
																val jobs = repository.getJobsBySearch(text, limit).toMutableList()
																updateJobsState(UiState.Success(jobs))
												} catch (e: Exception) {
																updateJobsState(UiState.Failure(e.message))
												}
								}

				fun searchJobsReload(text: String, offset: Int, limit: Int) =
								viewModelScope.launch(dispatcher) {
												val previous = state.value.jobs.getDataOrNull() ?: emptyList()
												try {
																val new = repository.getJobsBySearch(text, offset, limit)
																if (new.isEmpty()) return@launch
																updateJobsState(UiState.Success(previous + new))
												} catch (e: Exception) {
																updateJobsState(UiState.Failure(e.message))
												}
								}

				fun updateJobsState(state: UiState<List<Job>>) {
								_state.update { it.copy(jobs = state) }
				}

				fun resetState() {
								_state.update { SearchViewModelState() }
				}
}
