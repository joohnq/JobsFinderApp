package com.joohnq.jobsfinderapp.model.repository

import com.joohnq.jobsfinderapp.model.entity.Job
import com.joohnq.jobsfinderapp.model.source.remote.JobRemoteDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

class JobRepository @Inject constructor(
    private val remoteDataSource: JobRemoteDataSource,
    private val ioDispatcher: CoroutineDispatcher
) {
    suspend fun getAllPopularJobs(limit: Int): Flow<List<Job>> = flow {
        val res = remoteDataSource.service.allPopularJobs(limit.toString())

        if (!res.isSuccessful) {
            throw Exception(res.message())
        }

        emit(res.body() ?: throw Exception("Body is NULL"))
    }.flowOn(ioDispatcher)

    suspend fun getAllRecentPostedJobs(limit: Int): Flow<List<Job>> = flow {
        val res = remoteDataSource.service.allRecentPostedJobs(limit.toString())

        if (!res.isSuccessful) {
            throw Exception(res.message())
        }

        emit(res.body() ?: throw Exception("Body is NULL"))
    }.flowOn(ioDispatcher)

    suspend fun getJobDetail(id: String): Flow<Job> = flow {
        val res = remoteDataSource.service.getJobDetail(id)

        if (!res.isSuccessful) {
            throw Exception(res.message())
        }

        emit(res.body() ?: throw Exception("Body is NULL"))
    }.flowOn(ioDispatcher)

    suspend fun searchJob(
        title: String?,
        category: String?,
        company: String?,
        location: String?,
        salaryEntry: String?,
        salaryEnd: String?,
        type: String?,
    ): Flow<List<Job>?> = flow {
        val res = remoteDataSource.service.searchJob(
            title,
            category,
            company,
            location,
            salaryEntry,
            salaryEnd,
            type
        )

        if (!res.isSuccessful) {
            throw Exception(res.message())
        }

        emit(res.body() ?: throw Exception("Body is NULL"))
    }.flowOn(ioDispatcher)
}