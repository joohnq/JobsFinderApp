package com.joohnq.auth.data.di
import com.joohnq.auth.data.AuthenticateDataSource
import com.joohnq.auth.data.service.AuthenticationService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {
				@Provides
				@Singleton
				fun provideAuthenticateDataSource(retrofit: Retrofit): AuthenticateDataSource =
								AuthenticateDataSource(retrofit)

				@Provides
				@Singleton
				fun provideAuthenticateService(
								authenticateDataSource: AuthenticateDataSource
				): AuthenticationService = authenticateDataSource.authenticateService
}
