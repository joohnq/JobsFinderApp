package com.joohnq.favorite_ui.di

import com.joohnq.favorite_data.repository.FavoritesRepository
import com.joohnq.favorite_ui.viewmodel.FavoritesViewModel
import com.joohnq.data.repository.JobRepository
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