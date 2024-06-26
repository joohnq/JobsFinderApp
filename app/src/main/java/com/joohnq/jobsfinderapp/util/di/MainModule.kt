package com.joohnq.jobsfinderapp.util.di

import android.content.Context
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.joohnq.jobsfinderapp.util.EmailValidator
import com.joohnq.jobsfinderapp.util.PasswordValidator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object MainModule {

    @Provides
    @Singleton
    fun provideIoDispatchers(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    @Singleton
    fun provideSignInClient(
        @ApplicationContext context: Context,
    ): SignInClient = Identity.getSignInClient(context)

    @Provides
    @Singleton
    fun providePasswordValidator(): PasswordValidator = PasswordValidator()

    @Provides
    @Singleton
    fun provideEmailValidator(): EmailValidator = EmailValidator()
}
