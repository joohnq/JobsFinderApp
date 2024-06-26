package com.joohnq.jobsfinderapp.util.di

import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.joohnq.jobsfinderapp.model.repository.AuthRepository
import com.joohnq.jobsfinderapp.model.repository.DatabaseRepository
import com.joohnq.jobsfinderapp.model.repository.JobRepository
import com.joohnq.jobsfinderapp.model.repository.StorageRepository
import com.joohnq.jobsfinderapp.model.source.remote.JobRemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {
    @Provides
    @Singleton
    fun provideJobRemoteDataSource(): JobRemoteDataSource = JobRemoteDataSource()

    @Provides
    @Singleton
    fun provideJobRepository(
        remoteDataSource: JobRemoteDataSource,
        ioDispatcher: CoroutineDispatcher
    ): JobRepository = JobRepository(remoteDataSource, ioDispatcher)

    @Provides
    @Singleton
    fun provideAuthRepository(
        firebaseAuth: FirebaseAuth,
    ): AuthRepository = AuthRepository(firebaseAuth)

    @Provides
    @Singleton
    fun provideStorageRepository(
        firebaseAuth: FirebaseAuth,
        firebaseStorage: FirebaseStorage
    ): StorageRepository = StorageRepository(firebaseAuth, firebaseStorage)

    @Provides
    @Singleton
    fun provideDatabaseRepository(
        firebaseAuth: FirebaseAuth,
        firebaseFirestore: FirebaseFirestore
    ): DatabaseRepository = DatabaseRepository(firebaseAuth, firebaseFirestore)
}


