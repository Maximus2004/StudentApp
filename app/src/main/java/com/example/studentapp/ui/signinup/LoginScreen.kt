package com.example.studentapp.ui.signinup

import androidx.compose.foundation.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowInsetsCompat
import com.example.studentapp.R
import com.example.studentapp.ui.TextInput
import com.example.studentapp.ui.navigation.NavigationDestination

object LoginScreen : NavigationDestination {
    override val route: String = "LoginScreen"
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun LoginScreen(
    onClickAuthButton: (Boolean) -> Unit,
    onClickRegisterButton: () -> Unit,
    onEmailChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    password: String,
    email: String,
    isEmailError: Boolean,
    isPasswordError: Boolean
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
                    .padding(top = 88.dp, start = 14.dp, end = 14.dp)
                    .wrapContentHeight()
                    .alpha(0.80f),
                shape = MaterialTheme.shapes.medium,
                elevation = 13.dp
            ) {

                val focusRequester1 = remember { FocusRequester() }
                val focusRequester2 = remember { FocusRequester() }
                val keyboardController = LocalSoftwareKeyboardController.current

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(bottom = 32.dp)
                ) {
                    Text(
                        text = "Вход",
                        modifier = Modifier
                            .padding(top = 32.dp),
                        style = MaterialTheme.typography.h2
                    )
                    TextInput(
                        onDataChanged = { onEmailChanged(it) },
                        currentValue = email,
                        inputHint = "Почта",
                        keyboardActions = KeyboardActions(onNext = {
                            focusRequester2.requestFocus()
                        }),
                        focusRequester = focusRequester1,
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                        keyboardTransformation = VisualTransformation.None,
                        isError = isEmailError
                    )
                    TextInput(
                        onDataChanged = { onPasswordChanged(it) },
                        currentValue = password,
                        inputHint = "Пароль",
                        keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() }),
                        focusRequester = focusRequester2,
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                        keyboardTransformation = PasswordVisualTransformation(),
                        isError = isPasswordError
                    )
                }
            }
            Row(modifier = Modifier.padding(top = 20.dp)) {
                Text(text = "Ещё нет аккаунта? ", style = MaterialTheme.typography.body1)
                Text(text = "Регистрация",
                    style = MaterialTheme.typography.body2,
                    modifier = Modifier.clickable {
                        onClickRegisterButton()
                    }
                )
            }
            Button(
                onClick = { onClickAuthButton(true) },
                modifier = Modifier
                    .padding(top = 20.dp, bottom = 113.dp)
                    .size(width = 263.dp, height = 54.dp),
                shape = RoundedCornerShape(60.dp)
            ) {
                Text(text = "Войти")
            }
        }
    }
}