package com.example.studentapp.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore

interface AppContainer {
    val projectItemsRepository: ItemsRepository
    var userPreferencesRepository: UserPreferencesRepository
    var teamItemsRepository: TeamRepository
}

class AppDataContainer(context: Context, dataStore: DataStore<Preferences>) : AppContainer {
    override val projectItemsRepository: ItemsRepository = ProjectItemsRepository()
    override var userPreferencesRepository: UserPreferencesRepository = UserPreferencesRepository(dataStore)
    override var teamItemsRepository: TeamRepository = TeamItemsRepository()
}