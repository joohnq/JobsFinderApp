package com.joohnq.jobsfinderapp.model.repository

import com.joohnq.jobsfinderapp.model.entity.Job
import com.joohnq.jobsfinderapp.model.source.remote.JobRemoteDataSource
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class JobRepository @Inject constructor(
    private val remoteDataSource: JobRemoteDataSource,
) {
    suspend fun getAllPopularJobs(limit: String): Response<List<Job>> =
        remoteDataSource.service.allPopularJobs(limit)

    suspend fun getAllRecentPostedJobs(limit: String): Response<List<Job>> =
        remoteDataSource.service.allRecentPostedJobs(limit)

    suspend fun getJobDetail(id: String): Response<Job> = remoteDataSource.service.getJobDetail(id)
    suspend fun searchJob(
        title: String?,
        category: String?,
        company: String?,
        location: String?,
        salaryEntry: String?,
        salaryEnd: String?,
        type: String?,
    ): Response<List<Job>?> = remoteDataSource.service.searchJob(
        title,
        category,
        company,
        location,
        salaryEntry,
        salaryEnd,
        type
    )
}