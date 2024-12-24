package com.joohnq.job.data.service

import com.joohnq.job.domain.entity.Job
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface JobService {
				@GET("/jobs")
				suspend fun getAll(
								@Query("positionName") positionName: String? = null,
								@Query("type") type: String? = null,
								@Query("offset") offset: Int? = null,
								@Query("limit") limit: Int? = null,
				): Response<List<Job>>

				@GET("/jobs/{id}")
				suspend fun getById(@Path("id") id: String): Response<Job>
}