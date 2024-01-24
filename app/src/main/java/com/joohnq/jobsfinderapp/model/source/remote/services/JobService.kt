package com.joohnq.jobsfinderapp.model.source.remote.services

import com.joohnq.jobsfinderapp.model.entity.Job
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET

interface JobService {
    @GET("jobs/popular")
    fun getAllPopularJobs(): Observable<List<Job>>
    @GET("jobs/recent-post")
    fun getAllRecentPostedJobs(): Observable<List<Job>>
}