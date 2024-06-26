package com.joohnq.jobsfinderapp.util.di

import android.content.Context
import com.joohnq.jobsfinderapp.util.AlertDialog
import com.joohnq.jobsfinderapp.util.Toast
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object ComponentsModule {
    @Provides
    @Singleton
    fun provideAlertDialog(
        @ApplicationContext context: Context,
    ): AlertDialog = AlertDialog(context)

    @Provides
    @Singleton
    fun provideToast(
        @ApplicationContext context: Context,
    ): Toast = Toast(context)
}
