package com.joohnq.job_data

import com.joohnq.job_domain.entities.Job

interface JobsDatabaseRepository {
				suspend fun getRemoteJobs(): List<Job>
				suspend fun getFullTimeJobs(): List<Job>
				suspend fun getPartTimeJobs(): List<Job>
				suspend fun getHomeJobs(occupation: String): List<Job>
				suspend fun getJobById(id: String): Job
				suspend fun getJobsByIds(ids: List<String>): List<Job>
}