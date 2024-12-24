package com.joohnq.job.data.repository

import com.joohnq.job.domain.entity.Job

interface JobRepository {
				suspend fun getRemoteJobs(): List<Job>
				suspend fun getFullTimeJobs(): List<Job>
				suspend fun getPartTimeJobs(): List<Job>
				suspend fun getJobsBySearch(occupation: String, limit: Int): List<Job>
				suspend fun getJobsBySearch(occupation: String, offset: Int, limit: Int): List<Job>
				suspend fun getJobById(id: String): Job
				suspend fun getJobsByIds(ids: List<String>): List<Job>
}