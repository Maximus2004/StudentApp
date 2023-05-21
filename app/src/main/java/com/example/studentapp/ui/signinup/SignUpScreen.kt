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
import com.example.studentapp.data.ImageDownloadStatus
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
    onClickUploadAvatar: () -> Unit,
    avatar: Response,
    onClickUploadPortfolio: () -> Unit,
    portfolio: ImageDownloadStatus
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
                    when (avatar) {
                        is Response.Default -> Image(
                            painter = painterResource(id = R.drawable.unknown_avatar),
                            contentDescription = null,
                            modifier = Modifier
                                .padding(top = 22.dp)
                                .size(100.dp)
                                .clip(CircleShape)
                                .clickable { onClickUploadAvatar() }
                        )
                        is Response.Loading -> CircularProgressIndicator(
                            modifier = Modifier.padding(
                                top = 22.dp
                            )
                        )
                        is Response.Success ->
                            AsyncImage(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(avatar.avatarUri)
                                    .crossfade(true)
                                    .build(),
                                contentScale = ContentScale.Crop,
                                contentDescription = null,
                                modifier = Modifier
                                    .padding(top = 22.dp)
                                    .size(100.dp)
                                    .clip(CircleShape)
                                    .clickable { onClickUploadAvatar() }
                            )
                        is Response.Error -> Text(text = "Ваша фоточка слишком красивая для этого приложения")
                    }
                    Button(
                        onClick = { onClickUploadPortfolio() },
                        shape = MaterialTheme.shapes.medium,
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = MaterialTheme.colors.secondary,
                            contentColor = MaterialTheme.colors.onSecondary
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 24.dp, end = 24.dp, top = 25.dp)
                    ) {
                        Row(modifier = Modifier.padding(vertical = 10.dp)) {
                            Text(
                                text = "Загрузите портфолио",
                                style = MaterialTheme.typography.subtitle1
                            )
                            Spacer(modifier = Modifier.weight(1f))
                            Image(
                                painter = painterResource(id = R.drawable.upload_icon),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(17.dp)
                                    .padding(top = 4.dp)
                            )
                        }
                    }
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
            Box(modifier = Modifier.padding(top = 20.dp, bottom = 93.dp).size(width = 263.dp, height = 54.dp)) {
                Button(
                    onClick = { onClickRegisterButton() },
                    modifier = Modifier.fillMaxSize(),
                    shape = RoundedCornerShape(30.dp)
                ) {
                    if (portfolio != ImageDownloadStatus.Loading) Text(text = "Зарегистрироваться")
                }
                if (portfolio == ImageDownloadStatus.Loading)
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(30.dp)
                            .align(Alignment.Center),
                        color = Color.White
                    )
            }
        }
    }
}