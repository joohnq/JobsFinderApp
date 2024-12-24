package com.joohnq.job.data.repository

import com.joohnq.core.data.repository.executeOperation
import com.joohnq.job.data.service.JobService
import com.joohnq.job.domain.entity.Job
import javax.inject.Inject

class JobRepositoryImpl @Inject constructor(
				private val service: JobService
): JobRepository {
				override suspend fun getRemoteJobs(): List<Job> = executeOperation {
								service.getAll(type = "remote")
				}

				override suspend fun getFullTimeJobs(): List<Job> = executeOperation {
								service.getAll(type = "full_time")
				}

				override suspend fun getPartTimeJobs(): List<Job> = executeOperation {
								service.getAll(type = "part_time")
				}

				override suspend fun getJobsBySearch(occupation: String, limit: Int): List<Job> =
								executeOperation {
												service.getAll(positionName = occupation, limit = limit)
								}

				override suspend fun getJobsBySearch(occupation: String, offset: Int, limit: Int): List<Job> =
								executeOperation {
												service.getAll(positionName = occupation, offset = offset, limit = limit)
								}

				override suspend fun getJobById(id: String): Job = executeOperation {
								service.getById(id)
				}

				override suspend fun getJobsByIds(ids: List<String>): List<Job> {
								val jobs = ArrayList<Job>()
								for (id in ids) {
												val res = executeOperation {
																service.getById(id)
												}
												jobs.add(res)
								}
								return jobs
				}

}