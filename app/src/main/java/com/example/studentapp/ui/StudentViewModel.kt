package com.example.studentapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.studentapp.StudentApplication
import com.example.studentapp.R
import com.example.studentapp.data.UserPreferencesRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class StudentViewModel(private val userPreferencesRepository: UserPreferencesRepository) : ViewModel() {
    val uiState: StateFlow<DessertReleaseUiState> =
        userPreferencesRepository.isUserRegistered
            // получаем Flow из репозитория и переводим ео в UiState
            .map { isLinearLayout ->
                DessertReleaseUiState(isLinearLayout)
            }
            // переводим UiState в StateFlow
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = DessertReleaseUiState()
            )

    fun changeUser(isLinearLayout: Int) {
        viewModelScope.launch {
            userPreferencesRepository.saveUserPreferences(isLinearLayout)
        }
    }
}

data class DessertReleaseUiState(val isUserRegistered: Int = -1)