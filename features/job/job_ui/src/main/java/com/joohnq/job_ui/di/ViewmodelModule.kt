package com.joohnq.job_ui.di

import com.joohnq.job_data.repository.JobRepository
import com.joohnq.job_ui.viewmodel.JobsDetailViewModel
import com.joohnq.job_ui.viewmodel.JobsViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object ViewmodelModule {
				@Provides
				@Singleton
				fun provideJobsDetailViewModel(
								jobRepository: JobRepository,
								ioDispatcher: CoroutineDispatcher
				): JobsDetailViewModel =
								JobsDetailViewModel(jobRepository, ioDispatcher)

				@Provides
				@Singleton
				fun provideJobsViewModel(
								jobRepository: JobRepository,
								ioDispatcher: CoroutineDispatcher
				): JobsViewModel =
								JobsViewModel(jobRepository, ioDispatcher)
}

