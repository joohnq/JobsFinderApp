package com.joohnq.search.di

import com.joohnq.job_data.repository.JobRepository
import com.joohnq.job_ui.viewmodel.JobsDetailViewModel
import com.joohnq.search.viewmodel.FiltersViewModel
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
				fun provideJobsViewModel(
								jobRepository: JobRepository,
								ioDispatcher: CoroutineDispatcher,
								filtersViewModel: FiltersViewModel
				): SearchViewModel =
								SearchViewModel(jobRepository, ioDispatcher, filtersViewModel)

				@Provides
				@Singleton
				fun provideFiltersViewModel(
				): FiltersViewModel =
								FiltersViewModel()
}

