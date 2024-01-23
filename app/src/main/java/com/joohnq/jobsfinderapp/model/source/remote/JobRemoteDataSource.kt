package com.joohnq.jobsfinderapp.model.source.remote

import com.joohnq.jobsfinderapp.model.entity.Job
import com.joohnq.jobsfinderapp.model.source.remote.services.JobService
import io.reactivex.rxjava3.core.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class JobRemoteDataSource @Inject constructor() {
    private val service: JobService

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api-jobs-finder2.vercel.app/api/")
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        service = retrofit.create(JobService::class.java)
    }

    val jobs: Observable<List<Job>> = service.getAllPopularJobs()
}
