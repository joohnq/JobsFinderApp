package com.joohnq.user_data.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.joohnq.user_data.repository.UserRepository
import com.joohnq.user_data.repository.UserRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object UserRepositoryModule {
				@Provides
				@Singleton
				fun provideUserRepository(
								auth: FirebaseAuth,
								db: FirebaseFirestore,
								storage: FirebaseStorage
				): UserRepository = UserRepositoryImpl(auth = auth, db = db, storage = storage)
}


