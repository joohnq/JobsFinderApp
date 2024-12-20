package com.joohnq.data.repository

import android.content.Context
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential

class GoogleAuthRepository {
				suspend fun getFirebaseCredential(context: Context): GoogleIdTokenCredential {
								val credentialManager = CredentialManager.create(context)
								val googleIdOption = GetGoogleIdOption
												.Builder()
												.setFilterByAuthorizedAccounts(false)
												.setServerClientId("WEB_CLIENT_ID")
												.build()
								val request = GetCredentialRequest
												.Builder()
												.addCredentialOption(googleIdOption)
												.build()
								val result = credentialManager.getCredential(
												context = context,
												request = request
								)
								val googleIdTokenCredential =
												GoogleIdTokenCredential.createFrom(result.credential.data)

								return googleIdTokenCredential
				}
}