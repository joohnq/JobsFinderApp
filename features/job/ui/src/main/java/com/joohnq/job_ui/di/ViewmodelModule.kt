package com.joohnq.job_ui.di

import com.joohnq.data.repository.JobRepository
import com.joohnq.job_ui.viewmodel.JobsViewModel
import com.joohnq.job_ui.viewmodel.SearchViewModel
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
				fun provideJobsViewModel(
								jobRepository: JobRepository,
								ioDispatcher: CoroutineDispatcher
				): JobsViewModel =
								JobsViewModel(jobRepository, ioDispatcher)

				@Provides
				@Singleton
				fun provideSearchViewModel(
								jobRepository: JobRepository,
								ioDispatcher: CoroutineDispatcher,
				): SearchViewModel =
								SearchViewModel(jobRepository, ioDispatcher)
}

