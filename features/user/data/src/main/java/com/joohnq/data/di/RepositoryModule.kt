package com.joohnq.data.di

import com.joohnq.data.network.UserService
import com.joohnq.data.repository.UserRepository
import com.joohnq.data.repository.UserRepositoryImpl
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
				fun provideUserRepository(
								service: UserService,
				): UserRepository = UserRepositoryImpl(service = service)
}


