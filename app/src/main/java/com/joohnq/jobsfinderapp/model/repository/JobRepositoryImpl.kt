package com.joohnq.jobsfinderapp.model.repository

import android.util.Log
import com.joohnq.jobsfinderapp.model.entity.Job
import com.joohnq.jobsfinderapp.model.source.remote.JobRemoteDataSource
import com.joohnq.jobsfinderapp.util.UiState
import com.joohnq.jobsfinderapp.util.rx.CompositeDisposableExtensions.plusAssign
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.subjects.ReplaySubject
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class JobRepositoryImpl @Inject constructor(
    private val remoteDataSource: JobRemoteDataSource,
) : JobRepository {
    private val composite = CompositeDisposable()
    private val popularJobs: ReplaySubject<UiState<List<Job>>> = ReplaySubject.create(1)
    private val recentPostedJobs: ReplaySubject<UiState<List<Job>>> = ReplaySubject.create(1)

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

    private fun handleJobFetchError(error: Throwable) {
        Log.e("JobRepositoryImpl", error.toString())
    }

    override fun clearDisposables() {
        composite.clear()
    }

    private fun addDisposable(disposable: Disposable) {
        composite += disposable
    }

    override fun getAllPopularJobs(): Observable<UiState<List<Job>>> {
        return popularJobs
    }

    override fun getAllRecentPostedJobs(): Observable<UiState<List<Job>>> {
        return recentPostedJobs
    }
}