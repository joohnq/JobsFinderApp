package com.joohnq.data.di

import com.joohnq.data.repository.JobRepository
import com.joohnq.data.repository.JobRepositoryImpl
import com.joohnq.data.service.JobService
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
