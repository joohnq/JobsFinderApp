package com.joohnq.data.di
import com.joohnq.data.network.UserDataSource
import com.joohnq.data.network.UserService
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
				fun provideUserDataSource(retrofit: Retrofit): UserDataSource =
								UserDataSource(retrofit)

				@Provides
				@Singleton
				fun provideUserService(
								userDataSource: UserDataSource
				): UserService = userDataSource.userService
}
