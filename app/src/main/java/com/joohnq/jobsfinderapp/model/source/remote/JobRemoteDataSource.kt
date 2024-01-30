package com.joohnq.jobsfinderapp.model.source.remote

import com.joohnq.jobsfinderapp.model.source.remote.services.JobService
import com.joohnq.jobsfinderapp.util.Constants.RETROFIT_URL_BASE
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class JobRemoteDataSource @Inject constructor() {
    val service: JobService
    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(RETROFIT_URL_BASE)
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        service = retrofit.create(JobService::class.java)
    }
}
