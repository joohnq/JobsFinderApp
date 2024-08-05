package com.joohnq.job_data.services

import com.joohnq.job_domain.constants.JobConstants.RETROFIT_PATH_JOB_DETAIL
import com.joohnq.job_domain.constants.JobConstants.RETROFIT_PATH_POPULAR_JOBS
import com.joohnq.job_domain.constants.JobConstants.RETROFIT_PATH_RECENT_POSTED_JOBS
import com.joohnq.job_domain.constants.JobConstants.RETROFIT_PATH_SEARCH
import com.joohnq.job_domain.entities.Job
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface JobService {
    @GET(RETROFIT_PATH_POPULAR_JOBS)
    suspend fun allPopularJobs(@Query("limit") limit: String): Response<List<Job>>

    @GET(RETROFIT_PATH_RECENT_POSTED_JOBS)
    suspend fun allRecentPostedJobs(@Query("limit") limit: String): Response<List<Job>>

    @GET(RETROFIT_PATH_JOB_DETAIL)
    suspend fun getJobDetail(@Query("id") id: String): Response<Job>

    @GET(RETROFIT_PATH_SEARCH)
    suspend fun searchJob(
        @Query("title") title: String?,
        @Query("category") category: String?,
        @Query("company") company: String?,
        @Query("location") location: String?,
        @Query("salaryEntry") salaryEntry: String?,
        @Query("salaryEnd") salaryEnd: String?,
        @Query("type") type: String?,
    ): Response<List<Job>?>
}