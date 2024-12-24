package com.joohnq.user.ui.di

import com.joohnq.user.ui.viewmodel.UserViewModel
import com.joohnq.data.repository.UserRepository
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
								repository: UserRepository,
								dispatcher: CoroutineDispatcher
				): UserViewModel = UserViewModel(repository, dispatcher)
}