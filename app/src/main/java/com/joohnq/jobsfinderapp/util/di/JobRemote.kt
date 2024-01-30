package com.joohnq.jobsfinderapp.util.di

import com.joohnq.jobsfinderapp.model.entity.User
import com.joohnq.jobsfinderapp.model.repository.JobRepository
import com.joohnq.jobsfinderapp.model.repository.UserRepository
import com.joohnq.jobsfinderapp.model.source.remote.JobRemoteDataSource
import com.joohnq.jobsfinderapp.viewmodel.UserViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object JobRemote {

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
        jobRepository: JobRepository
    ): UserViewModel {
        return UserViewModel(userRepository,jobRepository)
    }

    @Provides
    @Singleton
    fun provideRemoteDataSource(): JobRemoteDataSource {
        return JobRemoteDataSource()
    }
}