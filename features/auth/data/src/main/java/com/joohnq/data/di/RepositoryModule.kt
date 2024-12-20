package com.joohnq.onboarding_data.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.joohnq.data.repository.AuthenticationRepository
import com.joohnq.data.repository.AuthenticationRepositoryImpl
import com.joohnq.data.repository.GoogleAuthRepository
import com.joohnq.data.repository.TokenRepository
import com.joohnq.data.service.AuthenticationService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {
				@Provides
				@Singleton
				fun provideAuthenticaitonRepository(
								authenticationService: AuthenticationService
				): AuthenticationRepository =
								AuthenticationRepositoryImpl(authenticationService = authenticationService)

				@Provides
				@Singleton
				fun provideGoogleAuthRepository(): GoogleAuthRepository = GoogleAuthRepository()

				@Provides
				@Singleton
				fun provideTokenRepository(
								dataStore: DataStore<Preferences>
				): TokenRepository = TokenRepository(dataStore = dataStore)
}


