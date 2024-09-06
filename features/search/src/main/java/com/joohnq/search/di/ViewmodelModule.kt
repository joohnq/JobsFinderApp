package com.joohnq.search.di

import com.joohnq.job_data.JobsDatabaseRepository
import com.joohnq.search.viewmodel.SearchViewModel
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
				fun provideSearchViewModel(
								jobsDatabaseRepository: JobsDatabaseRepository,
								ioDispatcher: CoroutineDispatcher,
				): SearchViewModel =
								SearchViewModel(jobsDatabaseRepository, ioDispatcher)
}

