package com.joohnq.job_data.di

import com.joohnq.job_data.services.JobService
import com.joohnq.job_domain.constants.JobConstants.RETROFIT_URL_BASE
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
				@Provides
				@Singleton
				fun provideRetrofit(): JobService = Retrofit.Builder()
								.baseUrl(RETROFIT_URL_BASE)
								.addConverterFactory(GsonConverterFactory.create())
								.build()
								.create(JobService::class.java)
}
