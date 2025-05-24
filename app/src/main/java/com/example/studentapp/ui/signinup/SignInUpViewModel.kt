package com.example.studentapp.ui.signinup

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.studentapp.data.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.*

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

    private fun checkEmailField(): Boolean {
        val check = '@' in uiState.value.email && uiState.value.email.isNotBlank()
        _uiState.update { it.copy(emailError = !check) }
        return check
    }

    private fun checkNameField(): Boolean {
        val check = uiState.value.name.isNotBlank()
        _uiState.update { it.copy(nameError = !check) }
        return check
    }

    private fun checkSurnameField(): Boolean {
        val check = uiState.value.surname.isNotBlank()
        _uiState.update { it.copy(surnameError = !check) }
        return check
    }

    private fun checkPasswordField(): Boolean {
        val check = uiState.value.password.isNotBlank() && uiState.value.password.length >= 6
        _uiState.update { it.copy(passwordError = !check) }
        return check
    }

    private fun checkDescriptionField(): Boolean {
        val check = uiState.value.description.isNotBlank()
        _uiState.update { it.copy(descriptionError = !check) }
        return check
    }

    private fun resetAuthData() {
        _uiState.update {
            it.copy(
                descriptionError = false,
                nameError = false,
                passwordError = false,
                surnameError = false,
                emailError = false
            )
        }
    }

    fun onClickRegisterButton(context: Context, onFinish: (String) -> Unit) {
        viewModelScope.launch {
            try {
                if (!checkNameField()) throw IllegalArgumentException("Заполните поле Имя")
                if (!checkSurnameField()) throw IllegalArgumentException("Заполните поле Фамилия")
                if (!checkEmailField()) throw IllegalArgumentException("Заполните поле Почта")
                if (!checkPasswordField()) throw IllegalArgumentException("Заполните поле Пароль")
                if (!checkDescriptionField()) throw IllegalArgumentException("Расскажите о себе!")
                userAuthRepository.signup(
                    name = uiState.value.name,
                    surname = uiState.value.surname,
                    description = uiState.value.description,
                    email = uiState.value.email,
                    password = uiState.value.password,
                    onComplete = { authResponse, errorMessage ->
                        if (authResponse != null) {
                            resetAuthData()
                            showToast(context, "Успешная регистрация")
                            onFinish(authResponse.login)
                        } else {
                            showToast(context, "Произошла ошибка: $errorMessage")
                        }
                    }
                )
            } catch (e: Exception) {
                Toast.makeText(context, e.localizedMessage, Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun onClickLoginButton(context: Context, onFinish: (String) -> Unit) {
        viewModelScope.launch {
            try {
                if (!checkEmailField()) throw IllegalArgumentException("Заполните поле Почта")
                if (!checkPasswordField()) throw IllegalArgumentException("Заполните поле Пароль")

                userAuthRepository.login(
                    uiState.value.email,
                    uiState.value.password
                ) { authResponse, errorMessage ->
                    if (authResponse != null) {
                        resetAuthData()
                        showToast(context, "Успешный вход")
                        onFinish(authResponse.login)
                    } else {
                        showToast(context, "Произошла ошибка: $errorMessage")
                    }
                }
            } catch (e: Exception) {
                Toast.makeText(context, e.localizedMessage, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showToast(context: Context, text: String) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }
}