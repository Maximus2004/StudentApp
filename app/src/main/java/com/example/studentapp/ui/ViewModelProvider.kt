package com.example.studentapp.ui

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.studentapp.StudentApplication
import com.example.studentapp.ui.home.HomeViewModel
import com.example.studentapp.ui.message.MessageViewModel
import com.example.studentapp.ui.profile.ProfileViewModel

object ViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            StudentViewModel(studentApplication().container.userPreferencesRepository)
        }
        initializer {
            HomeViewModel()
        }
        initializer {
            MessageViewModel()
        }
        initializer {
            ProfileViewModel(studentApplication().container.projectItemsRepository)
        }
    }
}

// как бы создаём объект класса AirportApplication, но каким-то специфичным методом
fun CreationExtras.studentApplication(): StudentApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as StudentApplication)