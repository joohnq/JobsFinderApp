package com.joohnq.jobsfinderapp.util.di

import android.content.Context
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.joohnq.jobsfinderapp.model.repository.AuthRepository
import com.joohnq.jobsfinderapp.model.repository.JobRepository
import com.joohnq.jobsfinderapp.model.repository.UserRepository
import com.joohnq.jobsfinderapp.model.source.remote.JobRemoteDataSource
import com.joohnq.jobsfinderapp.sign_in.GoogleAuthUiClient
import com.joohnq.jobsfinderapp.sign_in.SignInResult
import com.joohnq.jobsfinderapp.viewmodel.UserViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DependenceInjectionModule {
    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Provides
    @Singleton
    fun provideFirebaseFirestore(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }

    @Provides
    @Singleton
    fun provideFirebaseStorage(): FirebaseStorage {
        return FirebaseStorage.getInstance()
    }

    @Provides
    @Singleton
    fun provideSignInResult(): SignInResult {
        return SignInResult(
            data = null,
            errorMessage = null
        )
    }

    @Provides
    @Singleton
    fun provideSignInClient(
        @ApplicationContext context: Context,
    ): SignInClient {
        return Identity.getSignInClient(context)
    }

    @Provides
    @Singleton
    fun provideGoogleAuthUiClient(
        @ApplicationContext context: Context,
        signInClient: SignInClient,
        auth: FirebaseAuth
    ): GoogleAuthUiClient {
        return GoogleAuthUiClient(context, signInClient, auth)
    }

    @Provides
    @Singleton
    fun provideContext(
        @ApplicationContext context: Context,
    ): Context {
        return context
    }

    @Provides
    @Singleton
    fun provideJob(
        remoteDataSource: JobRemoteDataSource,
    ): JobRepository {
        return JobRepository(remoteDataSource)
    }

    @Provides
    @Singleton
    fun provideUserViewModel(
        userRepository: UserRepository,
    ): UserViewModel {
        return UserViewModel(userRepository)
    }

    @Provides
    @Singleton
    fun provideRemoteDataSource(): JobRemoteDataSource {
        return JobRemoteDataSource()
    }

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


