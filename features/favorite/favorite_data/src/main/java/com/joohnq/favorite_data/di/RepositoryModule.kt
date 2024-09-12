package com.joohnq.favorite_data.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.joohnq.favorite_data.repository.FavoritesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
				@Provides
				@Singleton
				fun provideFavoriteRepository(
								db: FirebaseFirestore,
								auth: FirebaseAuth
				): FavoritesRepository = FavoritesRepository(
								db = db,
								auth = auth
				)
}