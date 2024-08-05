package com.joohnq.onboarding_data.repository

import android.content.Context
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.joohnq.onboarding_domain.constants.OnBoardingConstants.WEB_CLIENT_ID
import com.joohnq.onboarding_domain.entities.AuthType
import com.joohnq.user_domain.entities.User
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class GoogleAuthRepository @Inject constructor(
				private val auth: FirebaseAuth,
) {
				suspend fun getFirebaseCredential(context: Context): AuthCredential {
								val credentialManager = CredentialManager.create(context)
								val googleIdOption = GetGoogleIdOption
												.Builder()
												.setFilterByAuthorizedAccounts(false)
												.setServerClientId(WEB_CLIENT_ID)
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

								return GoogleAuthProvider.getCredential(
												googleIdTokenCredential.idToken,
												null
								)
				}

				suspend fun signInWithGoogle(
								firebaseCredential: AuthCredential
				): User = suspendCoroutine { continuation ->
								try {
												auth
																.signInWithCredential(firebaseCredential)
																.addOnSuccessListener { snapshot ->
																				val firebaseUser = snapshot.user
																				continuation.resume(
																								User(
																												name = firebaseUser?.displayName ?: "",
																												email = firebaseUser?.email ?: "",
																												imageUrl = firebaseUser?.photoUrl.toString(),
																												id = firebaseUser?.uid ?: "",
																												authType = AuthType.GOOGLE
																								)
																				)
																}.addOnFailureListener {
																				continuation.resumeWithException(it)
																}
								} catch (e: Exception) {
												continuation.resumeWithException(e)
								}
				}
}