package com.joohnq.jobsfinderapp.di

import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.joohnq.jobsfinderapp.model.repository.auth.AuthRepository
import com.joohnq.jobsfinderapp.model.repository.auth.AuthRepositoryImpl
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
    fun provideAuth(
        firebaseAuth: FirebaseAuth,
        firebaseDatabase: FirebaseFirestore,
        oneTapClient: SignInClient
    ): AuthRepository {
        return AuthRepositoryImpl(firebaseAuth, firebaseDatabase, oneTapClient)
    }
}