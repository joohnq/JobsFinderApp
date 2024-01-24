package com.joohnq.jobsfinderapp.model.repository

import com.joohnq.jobsfinderapp.model.entity.Job
import io.reactivex.rxjava3.core.Observable

interface JobRepository{
    fun getAllPopularJobs(): Observable<List<Job>>
    fun getAllRecentPostedJobs(): Observable<List<Job>>
}