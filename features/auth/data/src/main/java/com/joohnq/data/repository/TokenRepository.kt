package com.joohnq.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TokenRepository @Inject constructor(
				private val dataStore: DataStore<Preferences>
) {
				suspend fun set(token: String): Boolean = try {
								dataStore.edit { preferences ->
												preferences[TOKEN_PREFERENCE] = token
								}
								true
				} catch (e: Exception) {
								false
				}

				suspend fun get(): Flow<String?> =
								dataStore.data.map { it[TOKEN_PREFERENCE] }

				companion object {
								private val TOKEN_PREFERENCE = stringPreferencesKey("token")
				}
}