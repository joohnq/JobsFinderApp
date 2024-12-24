package com.joohnq.data

import com.joohnq.data.service.JobService
import retrofit2.Retrofit
import javax.inject.Inject

class JobDataSource @Inject constructor(
				private val retrofit: Retrofit
) {
				val jobService = retrofit.create(JobService::class.java)
}