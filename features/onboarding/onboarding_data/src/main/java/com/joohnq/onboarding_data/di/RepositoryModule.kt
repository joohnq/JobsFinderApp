package com.joohnq.onboarding_data.di

import com.google.firebase.auth.FirebaseAuth
import com.joohnq.onboarding_data.repository.AuthRepository
import com.joohnq.onboarding_data.repository.GoogleAuthRepository
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
    fun provideAuthRepository(
        auth: FirebaseAuth
    ): AuthRepository = AuthRepository(auth = auth)

    @Provides
    @Singleton
    fun provideGoogleAuthRepository(
        auth: FirebaseAuth
    ): GoogleAuthRepository = GoogleAuthRepository(auth = auth)
}


