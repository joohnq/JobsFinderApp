package com.joohnq.core.di

import com.joohnq.core.di.annotation.BaseURL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {
				@Provides
				@Singleton
				@BaseURL
				fun provideBaseUrl(): String = "http://10.0.2.2:8080"

				@Provides
				@Singleton
				fun provideOkHttpClient(): OkHttpClient = OkHttpClient.Builder()
								.build()

				@Provides
				@Singleton
				fun provideGsonConverter(): Converter.Factory = GsonConverterFactory.create()

				@Provides
				@Singleton
				fun provideRetrofit(
								@BaseURL url: String,
								client: OkHttpClient,
								converter: Converter.Factory
				): Retrofit =
								Retrofit.Builder()
												.baseUrl(url)
												.client(client)
												.addConverterFactory(converter)
												.build()
}
