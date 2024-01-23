package com.joohnq.jobsfinderapp.util.di

import android.content.Context
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.FirebaseAuth
import com.joohnq.jobsfinderapp.sign_in.GoogleAuthUiClient
import com.joohnq.jobsfinderapp.sign_in.SignInResult
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object GoogleAuthModule {
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

}