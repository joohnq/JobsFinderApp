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
																like(
																				DatabaseConstants.COLUMN_JOB_TYPE,
																				"%${DatabaseConstants.VALUE_FULL_TIME}%"
																)
																like(
																				DatabaseConstants.COLUMN_JOB_TYPE,
																				"%${DatabaseConstants.VALUE_TEMPO_INTEGRAL}%"
																)
												}
								}.decodeList<Job>()
				}

				override suspend fun getFullTimeJobs(): List<Job> {
								return database.select {
												filter {
																like(
																				DatabaseConstants.COLUMN_JOB_TYPE,
																				"%${DatabaseConstants.VALUE_FULL_TIME}%"
																)
																like(
																				DatabaseConstants.COLUMN_JOB_TYPE,
																				"%${DatabaseConstants.VALUE_TEMPO_INTEGRAL}%"
																)
												}
								}.decodeList<Job>()
				}

				override suspend fun getPartTimeJobs(): List<Job> {
								return database.select {
												filter {
																like(
																				DatabaseConstants.COLUMN_JOB_TYPE,
																				"%${DatabaseConstants.VALUE_PART_TIME}%"
																)
																like(
																				DatabaseConstants.COLUMN_JOB_TYPE,
																				"%${DatabaseConstants.VALUE_MEIO_PERIODO}%"
																)
												}
								}.decodeList<Job>()
				}

				override suspend fun getHomeJobs(occupation: String): List<Job> {
								return database.select {
												limit(count = 20)
												filter {
																like(DatabaseConstants.COLUMN_POSITION_NAME, "%${occupation}%")
																like(DatabaseConstants.COLUMN_DESCRIPTION, "%${occupation}%")
												}
								}.decodeList<Job>()
				}

				override suspend fun getJobById(id: String): Job {
								return database.select {
												filter {
																eq(DatabaseConstants.COLUMN_ID, id)
												}
								}.decodeSingle<Job>()
				}

				override suspend fun getJobsByIds(ids: List<String>): List<Job> {
								return database.select {
												filter {
																isIn(DatabaseConstants.COLUMN_ID, ids)
												}
								}.decodeList<Job>()
				}
}