package com.example.studentapp

import android.app.Application
import android.content.Context
import androidx.core.content.edit
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.studentapp.data.AppContainer
import com.example.studentapp.data.AppDataContainer

// создаём DataStore Preferences со следующим именем
private const val USER_PREFERENCE_NAME = "user_preferences"
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = USER_PREFERENCE_NAME)

// точка входа в приложение, нужно для того, чтобы init dependencies before launching MainActivity
class StudentApplication : Application() {
    lateinit var container : AppContainer
    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this, dataStore)
    }
}