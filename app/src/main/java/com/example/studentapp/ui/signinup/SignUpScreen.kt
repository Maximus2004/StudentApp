package com.example.studentapp.ui.signinup

import androidx.compose.foundation.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.*
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.studentapp.R
import com.example.studentapp.ui.TextInput
import com.example.studentapp.ui.navigation.NavigationDestination

object SignUpScreen : NavigationDestination {
    override val route: String = "SignUpScreen"
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SignUpScreen(
    isKeyboardOpen: Boolean,
    onClickAuthButton: () -> Unit,
    onClickRegisterButton: () -> Unit,
    onNameChanged: (String) -> Unit,
    onSurnameChanged: (String) -> Unit,
    onEmailChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    onDescriptionChanged: (String) -> Unit,
    name: String,
    surname: String,
    email: String,
    password: String,
    description: String,
    isNameError: Boolean,
    isSurnameError: Boolean,
    isPasswordError: Boolean,
    isDescriptionError: Boolean,
    isEmailError: Boolean,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.background_bubbles),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(bottom = if (isKeyboardOpen) 240.dp else 0.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                modifier = Modifier
                    .padding(top = 68.dp, start = 14.dp, end = 14.dp)
                    .wrapContentHeight()
                    .alpha(0.80f),
                shape = MaterialTheme.shapes.medium,
                elevation = 13.dp
            ) {
                val focusRequester1 = remember { FocusRequester() }
                val focusRequester2 = remember { FocusRequester() }
                val focusRequester3 = remember { FocusRequester() }
                val focusRequester4 = remember { FocusRequester() }
                val focusRequester5 = remember { FocusRequester() }
                val keyboardController = LocalSoftwareKeyboardController.current

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "Регистрация",
                        modifier = Modifier.padding(top = 32.dp),
                        style = MaterialTheme.typography.h2
                    )
                    TextInput(
                        onDataChanged = { onNameChanged(it) },
                        currentValue = name,
                        inputHint = "Имя*",
                        keyboardActions = KeyboardActions(onNext = {
                            focusRequester2.requestFocus()
                        }),
                        focusRequester = focusRequester1,
                        isError = isNameError
                    )
                    TextInput(
                        onDataChanged = { onSurnameChanged(it) },
                        currentValue = surname,
                        inputHint = "Фамилия*",
                        keyboardActions = KeyboardActions(onNext = {
                            focusRequester3.requestFocus()
                        }),
                        focusRequester = focusRequester2,
                        isError = isSurnameError
                    )
                    TextInput(
                        onDataChanged = { onEmailChanged(it) },
                        currentValue = email,
                        inputHint = "Почта*",
                        keyboardActions = KeyboardActions(onNext = {
                            focusRequester4.requestFocus()
                        }),
                        focusRequester = focusRequester3,
                        isError = isEmailError
                    )
                    TextInput(
                        onDataChanged = { onPasswordChanged(it) },
                        currentValue = password,
                        inputHint = "Пароль*", keyboardActions = KeyboardActions(onNext = {
                            focusRequester5.requestFocus()
                        }), focusRequester = focusRequester4,
                        keyboardTransformation = PasswordVisualTransformation(),
                        isError = isPasswordError
                    )
                    TextInput(
                        onDataChanged = { onDescriptionChanged(it) },
                        currentValue = description,
                        inputHint = "Расскажите о себе*",
                        keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() }),
                        focusRequester = focusRequester5,
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                        isError = isDescriptionError,
                        modifier = Modifier.padding(bottom = 25.dp)
                    )
                }
            }
            Row(modifier = Modifier.padding(top = 30.dp)) {
                Text(text = "Уже есть аккаунт? ", style = MaterialTheme.typography.body1)
                Text(
                    text = "Войти",
                    style = MaterialTheme.typography.body2,
                    modifier = Modifier.clickable {
                        onClickAuthButton()
                    }
                )
            }
            Box(
                modifier = Modifier
                    .padding(top = 20.dp, bottom = 93.dp)
                    .size(width = 263.dp, height = 54.dp)
            ) {
                Button(
                    onClick = { onClickRegisterButton() },
                    modifier = Modifier.fillMaxSize(),
                    shape = RoundedCornerShape(30.dp)
                ) {
                    Text(text = "Зарегистрироваться")
                }
            }
        }
    }
}