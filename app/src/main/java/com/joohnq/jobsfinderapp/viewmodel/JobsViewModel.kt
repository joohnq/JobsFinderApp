package com.joohnq.jobsfinderapp.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.joohnq.jobsfinderapp.util.rx.CompositeDisposableExtensions.plusAssign
import com.joohnq.jobsfinderapp.model.entity.Job
import com.joohnq.jobsfinderapp.model.repository.JobRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable
import javax.inject.Inject

@HiltViewModel
class JobsViewModel @Inject constructor(
    jobRepository: JobRepository
) : ViewModel() {
    private val composite = CompositeDisposable()
    val jobs = MutableLiveData<List<Job>>()

    init {
        composite += jobRepository.getAllPopularJobs().subscribe(
            { jobs.postValue(it) },
            { error -> Log.e("JobViewModel", error.message.toString()) })
    }

    override fun onCleared() {
        super.onCleared()
        composite.dispose()
    }

}