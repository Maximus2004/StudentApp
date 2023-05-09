package com.example.studentapp.data

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

interface UserRepository {
    val isUserRegistered: Flow<String>
    suspend fun saveUserPreferences(isUserRegistered: String)
}

// создаётся при создании приложения, чтобы задать все репозитории
class UserPreferencesRepository(private val dataStore: DataStore<Preferences>) : UserRepository {
    // data - Flow<Preferences> каждый раз, когда меняются данные в DataStore, во Flow передаётся новый объект Preferences
    // map достаёт каждый Preference из Flow и извлекает из него значение Flow<Boolean>
    override val isUserRegistered: Flow<String> = dataStore.data
        .catch {
            if (it is IOException) {
                Log.e(TAG, "Error reading preferences.", it)
                emit(emptyPreferences())
            } else {
                throw it
            }
        }
        .map { preferences -> preferences[IS_USER_REGISTERED] ?: "" }

    private companion object {
        val IS_USER_REGISTERED = stringPreferencesKey("is_user_registered")
        const val TAG = "UserPreferencesRepo"
    }

    // функция edit работает атомарно (хз, чё это значит)
    override suspend fun saveUserPreferences(isUserRegistered: String) {
        // preferences - это объект типа key-value
        dataStore.edit { preferences ->
            preferences[IS_USER_REGISTERED] = isUserRegistered
        }
    }
}