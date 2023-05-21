package com.example.studentapp.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

interface AppContainer {
    val projectItemsRepository: ItemsRepository
    val userPreferencesRepository: UserPreferencesRepository
    val teamItemsRepository: TeamRepository
    val userAuthRepository: AuthRepository
    val messageRepository: MessRepository
    val feedbackItemsRepository: FeedbackRepository
}

class AppDataContainer(dataStore: DataStore<Preferences>) : AppContainer {
    override val projectItemsRepository: ItemsRepository = ProjectItemsRepository()
    override val userPreferencesRepository: UserPreferencesRepository = UserPreferencesRepository(dataStore)
    override val teamItemsRepository: TeamRepository = TeamItemsRepository()
    override val userAuthRepository: AuthRepository = UserAuthRepository(auth = Firebase.auth)
    override val messageRepository: MessRepository = MessageRepository()
    override val feedbackItemsRepository: FeedbackRepository = FeedbackItemsRepository()
}