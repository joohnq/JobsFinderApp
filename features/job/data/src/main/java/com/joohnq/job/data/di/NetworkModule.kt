package com.joohnq.job.data.di

import com.joohnq.job.data.JobDataSource
import com.joohnq.job.data.service.JobService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
				@Provides
				@Singleton
				fun provideJobDataSource(
								retrofit: Retrofit
				): JobDataSource = JobDataSource(retrofit = retrofit)

				@Provides
				@Singleton
				fun provideJobService(
								jobDataSource: JobDataSource
				): JobService = jobDataSource.jobService
}