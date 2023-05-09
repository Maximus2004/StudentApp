package com.example.studentapp.ui.signinup

import androidx.compose.foundation.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.studentapp.R
import com.example.studentapp.ui.TextInput
import com.example.studentapp.ui.navigation.NavigationDestination
import com.example.studentapp.ui.theme.StudentAppTheme

object SignUpScreen : NavigationDestination {
    override val route: String = "SignUpScreen"
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SignUpScreen(
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
    description: String
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
                .verticalScroll(rememberScrollState()),
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
                        modifier = Modifier
                            .padding(top = 32.dp),
                        style = MaterialTheme.typography.h2
                    )
                    TextInput(
                        onDataChanged = { onNameChanged(it) },
                        currentValue = name,
                        inputHint = "Имя",
                        keyboardActions = KeyboardActions(onNext = {
                            focusRequester2.requestFocus()
                        }),
                        focusRequester = focusRequester1,
                    )
                    TextInput(
                        onDataChanged = { onSurnameChanged(it) },
                        currentValue = surname,
                        inputHint = "Фамилия",
                        keyboardActions = KeyboardActions(onNext = {
                            focusRequester3.requestFocus()
                        }),
                        focusRequester = focusRequester2,
                    )
                    TextInput(
                        onDataChanged = { onEmailChanged(it) },
                        currentValue = email,
                        inputHint = "Почта",
                        keyboardActions = KeyboardActions(onNext = {
                            focusRequester4.requestFocus()
                        }),
                        focusRequester = focusRequester3,

                        )
                    TextInput(
                        onDataChanged = { onPasswordChanged(it) },
                        currentValue = password,
                        inputHint = "Пароль", keyboardActions = KeyboardActions(onNext = {
                            focusRequester5.requestFocus()
                        }), focusRequester = focusRequester4,
                        keyboardTransformation = PasswordVisualTransformation()
                    )
                    TextInput(
                        onDataChanged = { onDescriptionChanged(it) },
                        currentValue = description,
                        inputHint = "Расскажите о себе",
                        keyboardActions = KeyboardActions(onDone = {
                            keyboardController?.hide()
                        }),
                        focusRequester = focusRequester5,
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    )
                    Button(
                        onClick = { /*TODO*/ },
                        shape = MaterialTheme.shapes.medium,
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = MaterialTheme.colors.secondary,
                            contentColor = MaterialTheme.colors.onSecondary
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 24.dp, end = 24.dp, top = 25.dp, bottom = 25.dp)
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
            Button(
                onClick = { onClickRegisterButton() },
                modifier = Modifier
                    .padding(top = 20.dp, bottom = 113.dp)
                    .size(width = 263.dp, height = 54.dp),
                shape = RoundedCornerShape(30.dp)
            ) {
                Text(text = "Зарегистрироваться")
            }
        }
    }
}