package com.joohnq.home.di

import com.joohnq.home.viewmodel.HomeViewModel
import com.joohnq.job_data.JobsDatabaseRepository
import com.joohnq.user.user_ui.viewmodel.UserViewModel
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
				fun providePostgrestQueryBuilder(
								userViewModel: UserViewModel,
								jobsDatabaseRepository: JobsDatabaseRepository,
								ioDispatcher: CoroutineDispatcher
				): HomeViewModel =
								HomeViewModel(
												userViewModel = userViewModel,
												ioDispatcher = ioDispatcher,
												jobsDatabaseRepository = jobsDatabaseRepository
								)
}