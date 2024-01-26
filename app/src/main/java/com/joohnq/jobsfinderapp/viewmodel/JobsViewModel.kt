package com.joohnq.jobsfinderapp.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.joohnq.jobsfinderapp.util.rx.CompositeDisposableExtensions.plusAssign
import com.joohnq.jobsfinderapp.model.entity.Job
import com.joohnq.jobsfinderapp.model.repository.job.JobRepository
import com.joohnq.jobsfinderapp.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import javax.inject.Inject

@HiltViewModel
class JobsViewModel @Inject constructor(
    private val jobRepository: JobRepository
) : ViewModel() {
    private val composite = CompositeDisposable()
    val popularJobs = MutableLiveData<UiState<List<Job>>>()
    val recentPostedJobs = MutableLiveData<UiState<List<Job>>>()

    init {
        addDisposable(
            jobRepository.getAllPopularJobs().subscribe(
                { popularJobs.postValue(it) },
                { error -> popularJobs.postValue(UiState.Failure(error.toString())) }
            )
        )

        addDisposable(
            jobRepository.getAllRecentPostedJobs().subscribe(
                { recentPostedJobs.postValue(it) },
                { error -> recentPostedJobs.postValue(UiState.Failure(error.toString())) }
            )
        )
    }

    private fun handleJobFetchError(error: Throwable) {
        Log.e("JobViewModel", error.toString())
    }

    private fun addDisposable(disposable: Disposable) {
        composite += disposable
    }

    override fun onCleared() {
        super.onCleared()
        jobRepository.clearDisposables()
        composite.dispose()
    }

}