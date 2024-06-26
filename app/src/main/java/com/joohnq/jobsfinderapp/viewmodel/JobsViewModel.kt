package com.joohnq.jobsfinderapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joohnq.jobsfinderapp.model.entity.Job
import com.joohnq.jobsfinderapp.model.repository.JobRepository
import com.joohnq.jobsfinderapp.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

@HiltViewModel
class JobsViewModel @Inject constructor(
    private val jobRepository: JobRepository,
    private val filtersViewModel: FiltersViewModel,
    private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {
    private val _popularJobs = MutableLiveData<UiState<List<Job>>>()
    val popularJobs: LiveData<UiState<List<Job>>> get() = _popularJobs

    private val _recentPostedJobs = MutableLiveData<UiState<List<Job>>>()
    val recentPostedJobs: LiveData<UiState<List<Job>>> get() = _recentPostedJobs

    init {
        getPopularJobs(1)
        getRecentPostedJobs(1)
    }

    suspend fun getJobDetail(id: String): UiState<Job> = suspendCoroutine { continuation ->
        viewModelScope.launch(ioDispatcher) {
            jobRepository
                .getJobDetail(id)
                .catch { continuation.resume(UiState.Failure(it.message)) }
                .collect { continuation.resume(UiState.Success(it)) }
        }
    }

    suspend fun searchJob(title: String?): UiState<List<Job>> = suspendCoroutine { continuation ->
        viewModelScope.launch(ioDispatcher) {
            jobRepository.searchJob(
                title,
                filtersViewModel.category,
                filtersViewModel.company,
                filtersViewModel.location,
                filtersViewModel.salaryEntry,
                filtersViewModel.salaryEnd,
                filtersViewModel.type
            )
                .catch { continuation.resume(UiState.Failure(it.message)) }
                .collect { jobs ->
                    if (jobs == null) continuation.resume(UiState.Failure("Jobs not found"))
                    continuation.resume(UiState.Success(jobs!!))
                }
        }
    }


    fun getPopularJobs(page: Int) {
        viewModelScope.launch(ioDispatcher) {
            _popularJobs.value = UiState.Loading
            try {
                jobRepository
                    .getAllPopularJobs(page * 10)
                    .catch {
                        _popularJobs.value = UiState.Failure(it.message)
                    }
                    .collect {
                        _popularJobs.value = UiState.Success(it)
                    }
            } catch (e: Exception) {
                _popularJobs.value = UiState.Failure(e.message)
            }
        }
    }

    fun getRecentPostedJobs(page: Int) {
        viewModelScope.launch(ioDispatcher) {
            _popularJobs.value = UiState.Loading
            try {
                jobRepository
                    .getAllRecentPostedJobs(page * 10)
                    .catch {
                        _popularJobs.value = UiState.Failure(it.message)
                    }
                    .collect {
                        _popularJobs.value = UiState.Success(it)
                    }
            } catch (e: Exception) {
                _popularJobs.value = UiState.Failure(e.message)
            }
        }
    }
}
