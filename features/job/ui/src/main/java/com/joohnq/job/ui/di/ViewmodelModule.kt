package com.joohnq.job.ui.di

import com.joohnq.job.data.repository.JobRepository
import com.joohnq.ui.viewmodel.JobsViewModel
import com.joohnq.ui.viewmodel.SearchViewModel
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
								repository: JobRepository,
								dispatcher: CoroutineDispatcher
				): JobsViewModel =
								JobsViewModel(repository, dispatcher)

				@Provides
				@Singleton
				fun provideSearchViewModel(
								repository: JobRepository,
								dispatcher: CoroutineDispatcher,
				): SearchViewModel =
								SearchViewModel(repository, dispatcher)
}

