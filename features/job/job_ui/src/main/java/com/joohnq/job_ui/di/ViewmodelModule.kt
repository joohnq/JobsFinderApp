package com.joohnq.job_ui.di

import com.joohnq.job_data.JobsDatabaseRepository
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
				fun provideJobsViewModel(
								jobsDatabaseRepository: JobsDatabaseRepository,
								ioDispatcher: CoroutineDispatcher
				): JobsViewModel =
								JobsViewModel(jobsDatabaseRepository, ioDispatcher)
}

