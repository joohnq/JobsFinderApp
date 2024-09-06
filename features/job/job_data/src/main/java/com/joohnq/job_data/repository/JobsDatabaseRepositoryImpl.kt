package com.joohnq.job_data.repository

import com.joohnq.job_data.JobsDatabaseRepository
import com.joohnq.job_domain.constants.DatabaseConstants
import com.joohnq.job_domain.entities.Job
import io.github.jan.supabase.postgrest.query.PostgrestQueryBuilder
import javax.inject.Inject

class JobsDatabaseRepositoryImpl @Inject constructor(
				private val database: PostgrestQueryBuilder
): JobsDatabaseRepository {
				override suspend fun getRemoteJobs(): List<Job> {
								return database.select {
												filter {
																or {
																				Job::jobType ilike "%${DatabaseConstants.VALUE_REMOTE_JOB}%"
																				Job::jobType ilike "%${DatabaseConstants.VALUE_TEMPO_INTEGRAL}%"
																}
												}
								}.decodeList<Job>()
				}

				override suspend fun getFullTimeJobs(): List<Job> {
								return database.select {
												filter {
																or {
																				Job::jobType ilike "%${DatabaseConstants.VALUE_FULL_TIME}%"
																				Job::jobType ilike "%${DatabaseConstants.VALUE_TEMPO_INTEGRAL}%"
																}
												}
								}.decodeList<Job>()
				}

				override suspend fun getPartTimeJobs(): List<Job> {
								return database.select {
												filter {
																or {
																				Job::jobType ilike "%${DatabaseConstants.VALUE_PART_TIME}%"
																				Job::jobType ilike "%${DatabaseConstants.VALUE_MEIO_PERIODO}%"
																}
												}
								}.decodeList<Job>()
				}

				override suspend fun getJobsBySearch(occupation: String, limit: Long): List<Job> {
								return database.select {
												limit(count = limit)
												filter {
																or {
																				Job::positionName ilike "%${occupation}%"
																}
												}
								}.decodeList<Job>()
				}

				override suspend fun getJobsBySearch(occupation: String, offset: Long, limit: Long): List<Job> {
								return database.select {
												range(offset..limit)
												filter {
																or {
																				Job::positionName ilike "%${occupation}%"
																}
												}
								}.decodeList<Job>()
				}

				override suspend fun getJobById(id: String): Job {
								return database.select {
												filter {
																Job::id eq id
												}
								}.decodeSingle<Job>()
				}

				override suspend fun getJobsByIds(ids: List<String>): List<Job> {
								return database.select {
												filter {
																Job::id isIn ids
												}
								}.decodeList<Job>()
				}
}