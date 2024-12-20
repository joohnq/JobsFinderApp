package com.joohnq.user.user_ui.di

import com.joohnq.user.user_ui.viewmodel.UserViewModel
import com.joohnq.user_data.repository.UserRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ViewmodelModule {
				@Provides
				@Singleton
				fun provideUserViewModel(
								userRepository: UserRepositoryImpl,
								ioDispatcher: CoroutineDispatcher
				): UserViewModel = UserViewModel(userRepository, ioDispatcher)
}