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
    val savedSearchHistory: Flow<Set<String>>
    suspend fun saveUserPreferences(isUserRegistered: String)
    suspend fun saveSearchQuery(search: String)
    suspend fun onClearHistory()
}

// создаётся при создании приложения, чтобы задать все репозитории
class UserPreferencesRepository(private val dataStore: DataStore<Preferences>) : UserRepository {
    // data - Flow<Preferences> каждый раз, когда меняются данные в DataStore, во Flow передаётся новый объект Preferences
    // map достаёт каждый Preference из Flow и извлекает из него значение Flow<Boolean>
    override val isUserRegistered: Flow<String> = dataStore.data
        .map { preferences -> preferences[IS_USER_REGISTERED] ?: "" }

    override val savedSearchHistory: Flow<Set<String>> = dataStore.data
        .map { preferences -> preferences[SEARCH_HISTORY] ?: emptySet() }

    override suspend fun saveUserPreferences(isUserRegistered: String) {
        dataStore.edit { preferences ->
            preferences[IS_USER_REGISTERED] = isUserRegistered
        }
    }

    override suspend fun onClearHistory() {
        dataStore.edit { preferences ->
            preferences[SEARCH_HISTORY] = emptySet()
        }
    }

    override suspend fun saveSearchQuery(search: String) {
        dataStore.edit { preferences ->
            val temp = preferences[SEARCH_HISTORY]?.toMutableSet()
            temp?.add(search)
            preferences[SEARCH_HISTORY] = temp as? Set<String> ?: emptySet()
        }
    }

    private companion object {
        val IS_USER_REGISTERED = stringPreferencesKey("is_user_registered")
        val SEARCH_HISTORY = stringSetPreferencesKey("search_history")
    }
}