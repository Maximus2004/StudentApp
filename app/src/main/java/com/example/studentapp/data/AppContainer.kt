package com.example.studentapp.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences

interface AppContainer {
    val projectItemsRepository: ItemsRepository
    val userPreferencesRepository: UserPreferencesRepository
    val jobRepository: JobRepository
    val userAuthRepository: AuthRepository
    val connectRepository: ConnectRepository
}

class AppDataContainer(dataStore: DataStore<Preferences>) : AppContainer {
    override val projectItemsRepository: ItemsRepository = ProjectItemsRepository()
    override val userPreferencesRepository: UserPreferencesRepository = UserPreferencesRepository(dataStore)
    override val jobRepository: JobRepository = JobItemsRepository()
    override val userAuthRepository: AuthRepository = UserAuthRepository()
    override val connectRepository: ConnectRepository = ConnectItemsRepository()
}