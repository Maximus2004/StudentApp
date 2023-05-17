package com.example.studentapp.ui.signinup

import android.net.Uri

data class SignInUpUiState(
    val name: String = "",
    val surname: String = "",
    val email: String = "",
    val password: String = "",
    val description: String = "",
    val nameError: Boolean = false,
    val surnameError: Boolean = false,
    val emailError: Boolean = false,
    val passwordError: Boolean = false,
    val descriptionError: Boolean = false,
    val selectedImageUri: Uri? = null,
    val portfolio: List<String> = listOf(),
    val isPortfolioLoading: Boolean = false
)
