package com.joohnq.search.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joohnq.core.state.UiState
import com.joohnq.job_data.JobsDatabaseRepository
import com.joohnq.job_domain.entities.Job
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@HiltViewModel
class SearchViewModel @Inject constructor(
				private val jobRepository: JobsDatabaseRepository,
				private val ioDispatcher: CoroutineDispatcher,
				private val filtersViewModel: FiltersViewModel,
): ViewModel() {
				suspend fun searchJob(title: String?): UiState<List<Job>> = suspendCoroutine { continuation ->
								viewModelScope.launch(ioDispatcher) {
//												jobRepository.searchJob(
//																title,
//																filtersViewModel.category,
//																filtersViewModel.company,
//																filtersViewModel.location,
//																filtersViewModel.salaryEntry,
//																filtersViewModel.salaryEnd,
//																filtersViewModel.type
//												)
//																.catch { continuation.resume(UiState.Failure(it.message)) }
//																.collect { jobs ->
//																				if (jobs == null) continuation.resume(UiState.Failure("Jobs not found"))
//																				continuation.resume(UiState.Success(jobs!!))
//																}
								}
				}
}