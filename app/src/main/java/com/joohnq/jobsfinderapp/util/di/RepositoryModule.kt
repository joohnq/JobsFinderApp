package com.joohnq.jobsfinderapp.util.di

import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.joohnq.jobsfinderapp.model.repository.auth.AuthRepository
import com.joohnq.jobsfinderapp.model.repository.user.UserRepository
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
        userRepository: UserRepository,
        oneTapClient: SignInClient
    ): AuthRepository {
        return AuthRepository(firebaseAuth, userRepository, oneTapClient)
    }

    @Provides
    @Singleton
    fun provideUser(
        firebaseAuth: FirebaseAuth,
        firebaseFirestore: FirebaseFirestore,
        firebaseStorage: FirebaseStorage
    ): UserRepository {
        return UserRepository(firebaseAuth, firebaseFirestore, firebaseStorage)
    }
}