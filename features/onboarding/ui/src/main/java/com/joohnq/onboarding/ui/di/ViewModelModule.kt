package com.joohnq.onboarding.ui.di

import com.joohnq.auth.data.repository.AuthenticationRepository
import com.joohnq.auth.data.repository.TokenRepository
import com.joohnq.onboarding.ui.viewmodel.AuthViewModel
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
								repository: AuthenticationRepository,
								tokenRepository: TokenRepository,
								dispatcher: CoroutineDispatcher
				): AuthViewModel = AuthViewModel(
								repository = repository,
								tokenRepository = tokenRepository,
								dispatcher = dispatcher
				)
}