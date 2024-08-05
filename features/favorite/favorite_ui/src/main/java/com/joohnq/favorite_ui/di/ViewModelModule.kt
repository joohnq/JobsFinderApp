package com.joohnq.favorite_ui.di

import com.joohnq.favorite_data.repository.FavoriteRepository
import com.joohnq.favorite_ui.viewmodel.FavoritesViewModel
import com.joohnq.job_data.repository.JobRepository
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
								favoritesRepository: FavoriteRepository,
								jobRepository: JobRepository,
								ioDispatcher: CoroutineDispatcher
				): FavoritesViewModel = FavoritesViewModel(
								favoritesRepository = favoritesRepository,
								ioDispatcher = ioDispatcher,
								jobRepository = jobRepository
				)
}