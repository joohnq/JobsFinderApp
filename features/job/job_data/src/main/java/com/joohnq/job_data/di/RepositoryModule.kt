package com.joohnq.job_data.di

import com.joohnq.job_data.JobsDatabaseRepository
import com.joohnq.job_data.repository.JobsDatabaseRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.jan.supabase.postgrest.query.PostgrestQueryBuilder
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {
				@Provides
				@Singleton
				fun provideJobDatabaseRepository(
								database: PostgrestQueryBuilder,
				): JobsDatabaseRepository =
								JobsDatabaseRepositoryImpl(database)
}
