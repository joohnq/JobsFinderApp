package com.joohnq.application_ui.di

import com.joohnq.application_data.repository.ApplicationRepository
import com.joohnq.application_ui.viewmodel.ApplicationsViewModel
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
								applicationRepository: ApplicationRepository,
								ioDispatcher: CoroutineDispatcher
				): ApplicationsViewModel = ApplicationsViewModel(
								applicationRepository = applicationRepository,
								ioDispatcher = ioDispatcher
				)
}