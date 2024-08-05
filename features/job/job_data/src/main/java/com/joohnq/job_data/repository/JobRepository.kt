package com.joohnq.job_data.repository

import com.joohnq.job_data.services.JobService
import com.joohnq.job_domain.entities.Job
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class JobRepository @Inject constructor(
				private val service: JobService,
				private val ioDispatcher: CoroutineDispatcher
) {
				suspend fun fetchAllPopularJobs(limit: Int): Flow<List<Job>> = flow {
								val res = service.allPopularJobs(limit.toString())

								if (!res.isSuccessful) throw Exception(res.message())

								emit(res.body() ?: throw Exception("Body is NULL"))
				}.flowOn(ioDispatcher)

				suspend fun fetchAllRecentPostedJobs(limit: Int): Flow<List<Job>> = flow {
								val res = service.allRecentPostedJobs(limit.toString())

								if (!res.isSuccessful) throw Exception(res.message())

								emit(res.body() ?: throw Exception("Body is NULL"))
				}.flowOn(ioDispatcher)

				suspend fun fetchJobDetail(id: String): Flow<Job> = flow {
								val res = service.getJobDetail(id)

								if (!res.isSuccessful) throw Exception(res.message())

								emit(res.body() ?: throw Exception("Body is NULL"))
				}.flowOn(ioDispatcher)

				suspend fun fetchJobDetail(ids: List<String>): Flow<List<Job>> = flow {
								val jobs = ids.map {
												val res = service.getJobDetail(it)
												res.body() ?: throw Exception("Body is NULL")
								}

								emit(jobs)
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
								val res = service.searchJob(
												title,
												category,
												company,
												location,
												salaryEntry,
												salaryEnd,
												type
								)

								if (!res.isSuccessful) throw Exception(res.message())

								emit(res.body() ?: throw Exception("Body is NULL"))
				}.flowOn(ioDispatcher)
}