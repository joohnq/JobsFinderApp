package com.joohnq.job_data.di

import com.joohnq.job_data.repository.JobRepository
import com.joohnq.job_data.services.JobService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {
				@Provides
				@Singleton
				fun provideJobRepository(
								ioDispatcher: CoroutineDispatcher,
								service: JobService
				): JobRepository =
								JobRepository(ioDispatcher = ioDispatcher, service = service)
}
