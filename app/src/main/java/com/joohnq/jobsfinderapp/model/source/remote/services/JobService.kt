package com.joohnq.jobsfinderapp.model.source.remote.services

import com.joohnq.jobsfinderapp.model.entity.Job
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface JobService {
    @GET("jobs/popular")
    fun getAllPopularJobs(): Observable<List<Job>>
    @GET("jobs/recent-post")
    fun getAllRecentPostedJobs(): Observable<List<Job>>
    @GET("job")
    fun getJobById(
        @Query("id") id: String = "0",
    ): Observable<Job>
}