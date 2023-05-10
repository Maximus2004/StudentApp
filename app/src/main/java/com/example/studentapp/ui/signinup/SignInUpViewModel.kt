package com.example.studentapp.ui.signinup

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.studentapp.data.*
import com.example.studentapp.ui.home.TAG
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SignInUpViewModel(private val userAuthRepository: AuthRepository) : ViewModel() {
    private val _uiState = MutableStateFlow(SignInUpUiState())
    val uiState: StateFlow<SignInUpUiState> = _uiState

    fun onPasswordChanged(password: String) {
        _uiState.update {
            it.copy(password = password)
        }
    }
    fun onEmailChanged(email: String) {
        _uiState.update {
            it.copy(email = email)
        }
    }
    fun onDescriptionChanged(description: String) {
        _uiState.update {
            it.copy(description = description)
        }
    }
    fun onNameChanged(name: String) {
        _uiState.update {
            it.copy(name = name)
        }
    }
    fun onSurnameChanged(surname: String) {
        _uiState.update {
            it.copy(surname = surname)
        }
    }
    private fun validateLoginForm() = uiState.value.email.isNotBlank() && uiState.value.password.isNotBlank()

    private fun validateSignupForm() =
        uiState.value.email.isNotBlank() &&
        uiState.value.password.isNotBlank() &&
        uiState.value.name.isNotBlank() &&
        uiState.value.surname.isNotBlank() &&
        uiState.value.description.isNotBlank()

    fun onClickRegisterButton(context: Context, onFinish: () -> Unit) {
        viewModelScope.launch {
            userAuthRepository.signup(uiState.value.email, uiState.value.password) { isSuccessful ->
                if (isSuccessful && validateSignupForm()) {
                    Toast.makeText(
                        context,
                        "Успешная регистрация",
                        Toast.LENGTH_SHORT
                    ).show()
                    userAuthRepository.createNewUser(uiState.value.name, uiState.value.surname, uiState.value.description)
                    onFinish()
                } else {
                    Toast.makeText(
                        context,
                        "Проверьте введённые данные",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    fun onClickLoginButton(context: Context, onFinish: () -> Unit) {
        viewModelScope.launch {
            userAuthRepository.login(uiState.value.email, uiState.value.password) { isSuccessful ->
                if (isSuccessful && validateLoginForm()) {
                    Toast.makeText(
                        context,
                        "Успешный вход",
                        Toast.LENGTH_SHORT
                    ).show()
                    onFinish()
                } else {
                    Toast.makeText(
                        context,
                        "Проверьте введённые данные",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}