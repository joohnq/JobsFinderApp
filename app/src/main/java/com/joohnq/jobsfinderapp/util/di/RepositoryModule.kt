package com.joohnq.jobsfinderapp.util.di

import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.joohnq.jobsfinderapp.model.repository.AuthRepository
import com.joohnq.jobsfinderapp.model.repository.AuthRepositoryImpl
import com.joohnq.jobsfinderapp.model.repository.UserRepository
import com.joohnq.jobsfinderapp.model.repository.UserRepositoryImpl
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
        return AuthRepositoryImpl(firebaseAuth, userRepository, oneTapClient)
    }

    @Provides
    @Singleton
    fun provideUser(
        firebaseAuth: FirebaseAuth,
        firebaseFirestore: FirebaseFirestore,
    ): UserRepository {
        return UserRepositoryImpl(firebaseAuth, firebaseFirestore)
    }
}