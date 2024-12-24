package com.joohnq.auth.data.di
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.joohnq.auth.data.repository.AuthenticationRepository
import com.joohnq.auth.data.repository.AuthenticationRepositoryImpl
import com.joohnq.auth.data.repository.GoogleAuthRepository
import com.joohnq.auth.data.repository.TokenRepository
import com.joohnq.auth.data.service.AuthenticationService
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
				fun provideAuthenticationRepository(
								service: AuthenticationService
				): AuthenticationRepository =
								AuthenticationRepositoryImpl(service = service)

				@Provides
				@Singleton
				fun provideGoogleAuthRepository(): GoogleAuthRepository = GoogleAuthRepository()

				@Provides
				@Singleton
				fun provideTokenRepository(
								dataStore: DataStore<Preferences>
				): TokenRepository = TokenRepository(dataStore = dataStore)
}


