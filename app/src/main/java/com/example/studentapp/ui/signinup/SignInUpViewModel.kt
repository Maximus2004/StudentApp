package com.example.studentapp.ui.signinup

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.studentapp.data.*
import com.example.studentapp.ui.home.TAG
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.studentapp.R
import com.example.studentapp.ui.profile.ProfileViewModel
import com.example.studentapp.ui.profile.UserProjectsState
import kotlinx.coroutines.flow.*

class SignInUpViewModel(private val userAuthRepository: AuthRepository) : ViewModel() {
    private val _uiState = MutableStateFlow(SignInUpUiState())
    val uiState: StateFlow<SignInUpUiState> = _uiState
    private val _avatarState: MutableStateFlow<Response> = MutableStateFlow(Response.Default)
    val avatarState: StateFlow<Response> = _avatarState.asStateFlow()

    fun onPasswordChanged(password: String) {
        _uiState.update {
            it.copy(password = password)
        }
    }

    fun setProfilePhotos(uris: List<Uri>) = viewModelScope.launch {
        userAuthRepository.uploadProfilePhotos(uris).collect { response ->
            _uiState.update {
                val temp = uiState.value.portfolio.toMutableList()
                temp.add(response)
                it.copy(portfolio = temp)
            }
        }
    }

    fun uploadImageToFirebaseStorage(imageUri: Uri) = viewModelScope.launch {
        _avatarState.update { Response.Loading }
        userAuthRepository.setImage(imageUri).collect { response ->
            Log.d(TAG, response.toString())
            _avatarState.update { response }
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

    fun onClickRegisterButton(context: Context, onFinish: () -> Unit) {
        viewModelScope.launch {
            try {
                // первый этап проверки
                if (!checkNameField()) throw IllegalArgumentException("Заполните поле Имя")
                if (!checkSurnameField()) throw IllegalArgumentException("Заполните поле Фамилия")
                if (!checkEmailField()) throw IllegalArgumentException("Заполните поле Почта")
                if (!checkPasswordField()) throw IllegalArgumentException("Заполните поле Пароль")
                if (!checkDescriptionField()) throw IllegalArgumentException("Расскажите о себе!")
                userAuthRepository.signup(
                    uiState.value.email,
                    uiState.value.password
                ) { isSuccessful, errorMessage ->
                    if (isSuccessful) {
                        resetAuthData()
                        Toast.makeText(context, "Успешная регистрация", Toast.LENGTH_SHORT).show()
                        when (avatarState.value) {
                            is Response.Success -> userAuthRepository.createNewUser(
                                name = uiState.value.name,
                                surname = uiState.value.surname,
                                description = uiState.value.description,
                                avatar = (avatarState.value as Response.Success).avatarUri.toString(),
                                portfolio = uiState.value.portfolio
                            )
                            else -> userAuthRepository.createNewUser(
                                name = uiState.value.name,
                                surname = uiState.value.surname,
                                description = uiState.value.description,
                                portfolio = uiState.value.portfolio
                            )
                        }
                        onFinish()
                    } else {
                        // второй этап проверки
                        when (errorMessage) {
                            is FirebaseNetworkException -> showToast(
                                context,
                                "Проверьте подключение к сети"
                            )
                            is FirebaseAuthUserCollisionException -> showToast(
                                context,
                                "Пользователь с таким данными уже существует"
                            )
                            is FirebaseAuthInvalidCredentialsException -> showToast(
                                context,
                                "Неправильно введена Почта или Пароль"
                            )
                            else -> showToast(context, "Что-то пошло не так, попробуйте позже")
                        }
                    }
                }
            } catch (e: Exception) {
                Toast.makeText(context, e.localizedMessage, Toast.LENGTH_SHORT).show()
                e.printStackTrace()
            }
        }
    }

    private fun showToast(context: Context, text: String) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }

    fun onClickLoginButton(context: Context, onFinish: () -> Unit) {
        viewModelScope.launch {
            try {
                // первый этап проверки
                if (!checkEmailField()) throw IllegalArgumentException("Заполните поле Почта")
                if (!checkPasswordField()) throw IllegalArgumentException("Заполните поле Пароль")
                userAuthRepository.login(
                    uiState.value.email,
                    uiState.value.password
                ) { isSuccessful, errorMessage ->
                    if (isSuccessful) {
                        resetAuthData()
                        Toast.makeText(context, "Успешный вход", Toast.LENGTH_SHORT).show()
                        onFinish()
                    } else {
                        // второй этап проверки
                        when (errorMessage) {
                            is FirebaseNetworkException -> showToast(
                                context,
                                "Проверьте подключение к сети"
                            )
                            is FirebaseAuthInvalidUserException -> showToast(
                                context,
                                "Пользователь не найден"
                            )
                            is FirebaseAuthInvalidCredentialsException -> showToast(
                                context,
                                "Неправильно введена Почта или Пароль"
                            )
                            else -> showToast(context, "Что-то пошло не так, попробуйте позже")
                        }
                    }
                }
            } catch (e: Exception) {
                Toast.makeText(context, e.localizedMessage, Toast.LENGTH_SHORT).show()
                e.printStackTrace()
            }
        }
    }
}

data class UserAvatarState(val avatar: Uri)