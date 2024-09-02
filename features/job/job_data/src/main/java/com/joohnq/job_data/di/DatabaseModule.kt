package com.joohnq.job_data.di

import com.joohnq.job_data.BuildConfig
import com.joohnq.job_domain.constants.DatabaseConstants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.PropertyConversionMethod
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.PostgrestQueryBuilder
import javax.inject.Singleton
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {
				@Provides
				@Singleton
				fun providePostgrestQueryBuilder(supabaseClient: SupabaseClient): PostgrestQueryBuilder =
								supabaseClient.from(DatabaseConstants.TABLE_JOBS)

				@Provides
				@Singleton
				fun provideSupabaseClient(): SupabaseClient =
								createSupabaseClient(
												supabaseUrl = BuildConfig.PROJECT_URL,
												supabaseKey = BuildConfig.API_KEY,
								) {
												install(Postgrest) {
																propertyConversionMethod = PropertyConversionMethod.NONE
												}
												requestTimeout = 60.seconds
								}
}
