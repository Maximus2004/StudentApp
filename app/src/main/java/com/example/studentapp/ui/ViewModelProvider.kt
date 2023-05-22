package com.example.studentapp.ui

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.studentapp.StudentApplication
import com.example.studentapp.ui.home.HomeViewModel
import com.example.studentapp.ui.message.MessageViewModel
import com.example.studentapp.ui.profile.ProfileViewModel
import com.example.studentapp.ui.signinup.SignInUpViewModel
import com.example.studentapp.ui.search.SearchViewModel

object ViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            StudentViewModel(
                studentApplication().container.userPreferencesRepository,
                studentApplication().container.userAuthRepository
            )
        }
        initializer {
            HomeViewModel()
        }
        initializer {
            MessageViewModel(
                studentApplication().container.messageRepository,
                studentApplication().container.userAuthRepository,
                studentApplication().container.projectItemsRepository,
                studentApplication().container.teamItemsRepository
            )
        }
        initializer {
            ProfileViewModel(
                studentApplication().container.projectItemsRepository,
                studentApplication().container.teamItemsRepository,
                studentApplication().container.userAuthRepository,
                studentApplication().container.feedbackItemsRepository
            )
        }
        initializer {
            SearchViewModel(
                studentApplication().container.teamItemsRepository,
                studentApplication().container.projectItemsRepository,
                studentApplication().container.userAuthRepository,
                studentApplication().container.messageRepository
            )
        }
        initializer {
            SignInUpViewModel(studentApplication().container.userAuthRepository)
        }
    }
}

// как бы создаём объект класса AirportApplication, но каким-то специфичным методом
fun CreationExtras.studentApplication(): StudentApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as StudentApplication)