package com.joohnq.jobsfinderapp.model.repository

import com.joohnq.jobsfinderapp.model.entity.Job
import com.joohnq.jobsfinderapp.util.UiState
import io.reactivex.rxjava3.core.Observable

interface JobRepository{
    fun getAllPopularJobs(): Observable<UiState<List<Job>>>
    fun getAllRecentPostedJobs(): Observable<UiState<List<Job>>>
    fun clearDisposables()
}