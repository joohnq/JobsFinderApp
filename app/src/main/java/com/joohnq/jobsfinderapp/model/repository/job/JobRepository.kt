package com.joohnq.jobsfinderapp.model.repository.job

import com.joohnq.jobsfinderapp.model.entity.Job
import com.joohnq.jobsfinderapp.model.source.remote.JobRemoteDataSource
import com.joohnq.jobsfinderapp.util.UiState
import com.joohnq.jobsfinderapp.util.rx.CompositeDisposableExtensions.plusAssign
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.subjects.ReplaySubject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.rx3.await

@Singleton
class JobRepository @Inject constructor(
    private val remoteDataSource: JobRemoteDataSource,
) {
    private val coroutineScope = CoroutineScope(Dispatchers.IO)
    private val composite = CompositeDisposable()
    private val popularJobs = ReplaySubject.create<UiState<List<Job>>>(1)
    private val recentPostedJobs = ReplaySubject.create<UiState<List<Job>>>(1)

    init {
        popularJobs.onNext(UiState.Loading)
        recentPostedJobs.onNext(UiState.Loading)

        addDisposable(remoteDataSource.popularJobs.subscribe(
            { jobsList -> popularJobs.onNext(UiState.Success(jobsList)) },
            { error -> popularJobs.onNext(UiState.Failure(error.toString())) }
        ))

        addDisposable(remoteDataSource.recentPostedJobs.subscribe(
            { jobsList -> recentPostedJobs.onNext(UiState.Success(jobsList)) },
            { error -> recentPostedJobs.onNext(UiState.Failure(error.toString())) }
        ))
    }

    fun clearDisposables() {
        composite.clear()
    }

    private fun addDisposable(disposable: Disposable) {
        composite += disposable
    }

    fun getJobDetailsById(id: String, result: (UiState<Job>) -> Unit) {
        try {
            addDisposable(
                remoteDataSource.getJobById(id).subscribe(
                    { jobDetails -> result.invoke(UiState.Success(jobDetails)) },
                    { error -> result.invoke(UiState.Failure(error.toString())) }
                )
            )
        } catch (e: Exception) {
            result.invoke(UiState.Failure(e.toString()))
        }
    }

    fun getAllPopularJobs(): Observable<UiState<List<Job>>> {
        return popularJobs
    }

    fun getAllRecentPostedJobs(): Observable<UiState<List<Job>>> {
        return recentPostedJobs
    }
}