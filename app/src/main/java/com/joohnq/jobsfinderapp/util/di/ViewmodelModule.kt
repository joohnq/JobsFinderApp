package com.joohnq.jobsfinderapp.util.di

import com.joohnq.jobsfinderapp.model.repository.DatabaseRepository
import com.joohnq.jobsfinderapp.model.repository.StorageRepository
import com.joohnq.jobsfinderapp.viewmodel.FiltersViewModel
import com.joohnq.jobsfinderapp.viewmodel.UserViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object ViewmodelModule {
    @Provides
    @Singleton
    fun provideUserViewModel(
        storageRepository: StorageRepository,
        databaseRepository: DatabaseRepository,
        ioDispatcher: CoroutineDispatcher
    ): UserViewModel = UserViewModel(storageRepository, databaseRepository, ioDispatcher)

    @Provides
    @Singleton
    fun provideFiltersViewModel(): FiltersViewModel = FiltersViewModel()
}



