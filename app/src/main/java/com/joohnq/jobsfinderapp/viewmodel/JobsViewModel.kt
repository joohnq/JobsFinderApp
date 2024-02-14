package com.joohnq.jobsfinderapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joohnq.jobsfinderapp.model.entity.Job
import com.joohnq.jobsfinderapp.model.repository.JobRepository
import com.joohnq.jobsfinderapp.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class JobsViewModel @Inject constructor(
    private val jobRepository: JobRepository,
    private val userViewModel: UserViewModel
) : ViewModel() {
    private val _popularJobs = MutableLiveData<UiState<List<Job>>>()
    val popularJobs: LiveData<UiState<List<Job>>> get() = _popularJobs

    private val _recentPostedJobs = MutableLiveData<UiState<List<Job>>>()
    val recentPostedJobs: LiveData<UiState<List<Job>>> get() = _recentPostedJobs

    init {
        getPopularJobs(1)
        getRecentPostedJobs(1)
    }

    fun getJobDetail(id: List<String>, result: (UiState<MutableList<Job>>) -> Unit) {
        viewModelScope.launch {
            userViewModel.setFavoritesDetails(UiState.Loading)
            val jobsDetails = mutableListOf<Job>()
            id.forEach {
                jobRepository.getJobDetail(it).let { res ->
                    if (res.isSuccessful) {
                        jobsDetails.add(res.body()!!)
                    }
                }
            }

            result.invoke(UiState.Success(jobsDetails))
        }
    }

    fun searchJob(
        title: String?,
        category: String?,
        company: String?,
        location: String?,
        salaryEntry: String?,
        salaryEnd: String?,
        type: String?,
        result: (UiState<List<Job>>) -> Unit
    ) {
        viewModelScope.launch {
            jobRepository.searchJob(title, category, company, location, salaryEntry, salaryEnd, type)
                .let { res ->
                    if (res.code() == 404) {
                        result.invoke(UiState.Success(listOf()))
                    } else {
                        if (res.isSuccessful) {
                            val body = res.body()
                            if (body != null) {
                                result.invoke(UiState.Success(body))
                            }
                        } else {
                            result.invoke(UiState.Failure(res.message()))
                        }
                    }
                }
        }
    }


    fun getPopularJobs(page: Int) {
        viewModelScope.launch {
            _popularJobs.value = UiState.Loading
            jobRepository.getAllPopularJobs((page * 10).toString()).let { res ->
                val state = if (res.isSuccessful) UiState.Success(res.body()!!)
                else UiState.Failure(res.message())
                _popularJobs.postValue(state)
            }
        }
    }

    fun getRecentPostedJobs(page: Int) {
        viewModelScope.launch {
            _recentPostedJobs.value = UiState.Loading
            jobRepository.getAllRecentPostedJobs((page * 10).toString()).let { res ->
                val state = if (res.isSuccessful) UiState.Success(res.body()!!)
                else UiState.Failure(res.message())
                _recentPostedJobs.postValue(state)
            }
        }
    }
}
