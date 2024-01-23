package com.joohnq.jobsfinderapp.util.di

import com.joohnq.jobsfinderapp.model.repository.JobRepository
import com.joohnq.jobsfinderapp.model.repository.JobRepositoryImpl
import com.joohnq.jobsfinderapp.model.source.remote.JobRemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.reactivex.rxjava3.core.Scheduler
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object JobRemote {

    @Provides
    @Singleton
    fun provideJob(
        remoteDataSource: JobRemoteDataSource,
    ): JobRepository {
        return JobRepositoryImpl(remoteDataSource)
    }

    @Provides
    @Singleton
    fun provideRemoteDataSource(): JobRemoteDataSource {
        return JobRemoteDataSource()
    }
}