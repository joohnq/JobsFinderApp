package com.joohnq.onboarding_ui.di

import com.joohnq.data.repository.AuthenticationRepository
import com.joohnq.data.repository.TokenRepository
import com.joohnq.onboarding_ui.viewmodel.AuthViewModel
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
								authenticationRepository: com.joohnq.data.repository.AuthenticationRepository,
								tokenRepository: com.joohnq.data.repository.TokenRepository,
								dispatcher: CoroutineDispatcher
				): AuthViewModel = AuthViewModel(
								authenticationRepository = authenticationRepository,
								tokenRepository = tokenRepository,
								dispatcher = dispatcher
				)
}