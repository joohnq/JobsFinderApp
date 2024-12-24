package com.joohhq.main.ui.di

import com.joohnq.job.data.repository.JobRepository
import com.joohnq.main.viewmodel.HomeViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object ViewModelModule {
				@Provides
				@Singleton
				fun provideHomeViewModel(
								jobRepository: JobRepository,
								dispatcher: CoroutineDispatcher
				): HomeViewModel =
								HomeViewModel(
												dispatcher = dispatcher,
												repository = jobRepository
								)
}