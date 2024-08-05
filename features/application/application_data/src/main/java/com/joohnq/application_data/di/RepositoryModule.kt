package com.joohnq.application_data.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.joohnq.application_data.repository.ApplicationRepository
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
				fun provideApplicationRepository(
								auth: FirebaseAuth,
								db: FirebaseFirestore
				): ApplicationRepository = ApplicationRepository(auth = auth, db = db)
}


