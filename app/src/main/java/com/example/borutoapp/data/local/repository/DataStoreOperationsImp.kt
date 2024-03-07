package com.example.borutoapp.data.local.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.borutoapp.domain.repository.DataStoreOperations
import com.example.borutoapp.util.Constants.PREFERENCES_KEY
import com.example.borutoapp.util.Constants.PREFERENCE_NAME
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import okio.IOException

val Context.datastore: DataStore<Preferences> by preferencesDataStore(name = PREFERENCE_NAME)


class DataStoreOperationsImp(context: Context) : DataStoreOperations {

    private object PreferencesKey {
        val onboardingKey = booleanPreferencesKey(name = PREFERENCES_KEY)
    }

    private val datastore = context.datastore

    override suspend fun saveOnboardingState(completed: Boolean) {
        datastore.edit { preferences ->
            preferences[PreferencesKey.onboardingKey] = completed
        }
    }
    override fun readOnboardingState(): Flow<Boolean> {
        return datastore.data.catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
            .map { preferences ->
                val onboardingState = preferences[PreferencesKey.onboardingKey] ?: false
                onboardingState
            }
    }
}