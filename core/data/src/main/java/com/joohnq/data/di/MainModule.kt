package com.joohnq.core.di

import com.joohnq.core.di.annotation.DispatcherIO
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MainModule {
				@Provides
				@Singleton
				@DispatcherIO
				fun provideIoDispatcher(): CoroutineDispatcher = Dispatchers.IO
}