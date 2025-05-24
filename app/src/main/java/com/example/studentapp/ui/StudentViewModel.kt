package com.example.studentapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.studentapp.data.AuthRepository
import com.example.studentapp.data.UserPreferencesRepository
import com.example.studentapp.data.UserResponse
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class StudentViewModel(
    private val userPreferencesRepository: UserPreferencesRepository,
    private val userAuthRepository: AuthRepository
) : ViewModel() {
    val uiState: StateFlow<UserUiState> =
        userPreferencesRepository.isUserRegistered
            .map { isUserRegistered ->
                UserUiState(isUserRegistered)
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = UserUiState()
            )
    val userState: StateFlow<UserResponse> =
        uiState
            .map { isUserRegistered -> userAuthRepository.getUserById(isUserRegistered.isUserRegistered) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = UserResponse()
            )


    fun changeUser(isUserRegistered: String) {
        viewModelScope.launch {
            userPreferencesRepository.saveUserPreferences(isUserRegistered)
        }
    }
}

data class UserUiState(val isUserRegistered: String = "")