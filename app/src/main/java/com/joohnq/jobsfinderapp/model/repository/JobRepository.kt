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
    suspend fun getAllPopularJobs(): Response<List<Job>> = remoteDataSource.service.allPopularJobs()
    suspend fun getAllRecentPostedJobs(): Response<List<Job>> = remoteDataSource.service.allRecentPostedJobs()
    suspend fun getJobDetail(id: String): Response<Job> = remoteDataSource.service.getJobDetail(id)
}