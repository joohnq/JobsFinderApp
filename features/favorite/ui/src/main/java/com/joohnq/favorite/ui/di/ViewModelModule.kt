package com.joohnq.favorite.ui.di

import com.joohnq.favorite.data.repository.FavoritesRepository
import com.joohnq.favorite.ui.viewmodel.FavoritesViewModel
import com.joohnq.job.data.repository.JobRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ViewModelModule {
				@Provides
				@Singleton
				fun provideAuthViewModel(
								favoritesRepository: FavoritesRepository,
								jobRepository: JobRepository,
								ioDispatcher: CoroutineDispatcher
				): FavoritesViewModel = FavoritesViewModel(
								favoritesRepository = favoritesRepository,
								ioDispatcher = ioDispatcher,
								jobRepository = jobRepository
				)
}