package com.joohnq.auth.data.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DataStoreModule {
				@Provides
				@Singleton
				fun providePreferencesDataStore(
								@ApplicationContext context: Context
				): DataStore<Preferences> =
								PreferenceDataStoreFactory.create { context.preferencesDataStoreFile("preferences") }
}
