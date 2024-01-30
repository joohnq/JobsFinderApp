package com.joohnq.jobsfinderapp.model.source.remote.services

import com.joohnq.jobsfinderapp.model.entity.Job
import com.joohnq.jobsfinderapp.util.Constants.RETROFIT_PATH_JOB_DETAIL
import com.joohnq.jobsfinderapp.util.Constants.RETROFIT_PATH_POPULAR_JOBS
import com.joohnq.jobsfinderapp.util.Constants.RETROFIT_PATH_RECENT_POSTED_JOBS
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface JobService {
    @GET(RETROFIT_PATH_POPULAR_JOBS)
    suspend fun allPopularJobs(): Response<List<Job>>
    @GET(RETROFIT_PATH_RECENT_POSTED_JOBS)
    suspend fun allRecentPostedJobs(): Response<List<Job>>
    @GET(RETROFIT_PATH_JOB_DETAIL)
    suspend fun getJobDetail(@Query("id") id: String): Response<Job>
}