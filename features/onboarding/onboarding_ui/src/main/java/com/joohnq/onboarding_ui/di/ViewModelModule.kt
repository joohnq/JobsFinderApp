package com.joohnq.onboarding_ui.di

import com.joohnq.onboarding_data.repository.AuthRepository
import com.joohnq.onboarding_data.repository.GoogleAuthRepository
import com.joohnq.onboarding_ui.viewmodel.AuthViewModel
import com.joohnq.user.user_ui.viewmodel.UserViewModel
import com.joohnq.user_data.repository.UserRepository
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
								userViewModel: UserViewModel,
								authRepository: AuthRepository,
								authGoogleAuthRepository: GoogleAuthRepository,
								userRepository: UserRepository,
								ioDispatcher: CoroutineDispatcher
				): AuthViewModel = AuthViewModel(
								userViewModel = userViewModel,
								authRepository = authRepository,
								googleAuthRepository = authGoogleAuthRepository,
								userRepository = userRepository,
								ioDispatcher = ioDispatcher
				)
}