package com.joohnq.jobsfinderapp.model.repository

import android.util.Log
import com.joohnq.jobsfinderapp.model.entity.Job
import com.joohnq.jobsfinderapp.model.source.remote.JobRemoteDataSource
import com.joohnq.jobsfinderapp.util.rx.CompositeDisposableExtensions.plusAssign
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.subjects.ReplaySubject
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class JobRepositoryImpl @Inject constructor(
    remoteDataSource: JobRemoteDataSource,
) : JobRepository {
    private val composite = CompositeDisposable()
    private val popularJobs: ReplaySubject<List<Job>> = ReplaySubject.create(1)
    private val recentPostedJobs: ReplaySubject<List<Job>> = ReplaySubject.create(1)

    init {
        composite += remoteDataSource.popularJobs.subscribe(
            { jobsList -> popularJobs.onNext(jobsList) },
            { error -> Log.e("JobRepositoryImpl", "Error fetching jobs", error) }
        )
        composite += remoteDataSource.recentPostedJobs.subscribe(
            { jobsList -> recentPostedJobs.onNext(jobsList) },
            { error -> Log.e("JobRepositoryImpl", "Error fetching jobs", error) }
        )
    }

    override fun getAllPopularJobs(): Observable<List<Job>> {
        return popularJobs
    }

    override fun getAllRecentPostedJobs(): Observable<List<Job>> {
        return recentPostedJobs
    }
}