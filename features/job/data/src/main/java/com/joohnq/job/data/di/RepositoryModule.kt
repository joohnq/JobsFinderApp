package com.joohnq.job.data.di

import com.joohnq.job.data.repository.JobRepository
import com.joohnq.job.data.repository.JobRepositoryImpl
import com.joohnq.job.data.service.JobService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {
				@Provides
				@Singleton
				fun provideJobRepository(
								jobService: JobService,
				): JobRepository =
								JobRepositoryImpl(service = jobService)
}
