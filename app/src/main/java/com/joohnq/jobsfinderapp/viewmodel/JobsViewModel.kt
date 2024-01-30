package com.joohnq.jobsfinderapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.joohnq.jobsfinderapp.model.entity.Job
import com.joohnq.jobsfinderapp.model.repository.JobRepository
import com.joohnq.jobsfinderapp.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

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
        getPopularJobs()
        getRecentPostedJobs()
    }

    fun getJobDetail(id: List<String>){
        viewModelScope.launch {
            Log.i("getJobDetail", "Executou")
            userViewModel.setFavoritesDetails(UiState.Loading)
            val jobsDetails = mutableListOf<Job>()
            id.forEach {
                jobRepository.getJobDetail(it).let { res ->
                    if (res.isSuccessful){
                        jobsDetails.add(res.body()!!)
                    }
                }
            }
            userViewModel.setFavoritesDetails(UiState.Success(jobsDetails))
        }
    }

    private fun getPopularJobs() {
        viewModelScope.launch {
            _popularJobs.value = UiState.Loading
            jobRepository.getAllPopularJobs().let { res ->
                val state = if (res.isSuccessful) UiState.Success(res.body()!!)
                else UiState.Failure(res.message())
                _popularJobs.postValue(state)
            }
        }
    }

    private fun getRecentPostedJobs() {
        viewModelScope.launch {
            _recentPostedJobs.value = UiState.Loading
            jobRepository.getAllRecentPostedJobs().let { res ->
                val state = if (res.isSuccessful) UiState.Success(res.body()!!)
                else UiState.Failure(res.message())
                _recentPostedJobs.postValue(state)
            }
        }
    }
}
