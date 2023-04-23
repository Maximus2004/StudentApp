package com.example.studentapp

import android.gesture.GestureOverlayView.OnGesturePerformedListener
import androidx.compose.foundation.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusOrder
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.studentapp.ui.navigation.NavigationDestination
import com.example.studentapp.ui.theme.StudentAppTheme

object LoginScreen : NavigationDestination {
    override val route: String = "LoginScreen"
}

@Composable
fun LoginScreen(onClickAuthButton: () -> Unit, onClickRegisterButton: () -> Unit) {
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
                        "Почта",
                        keyboardActions = KeyboardActions(onNext = {
                            focusRequester2.requestFocus()
                        }),
                        focusRequester = focusRequester1,
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                        keyboardTransformation = VisualTransformation.None
                    )
                    TextInput(
                        "Пароль",
                        keyboardActions = KeyboardActions(onDone = {
                            onClickAuthButton()
                        }),
                        focusRequester = focusRequester2,
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                        keyboardTransformation = PasswordVisualTransformation()
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
                onClick = { onClickAuthButton() },
                modifier = Modifier
                    .padding(top = 20.dp, bottom = 113.dp)
                    .size(width = 263.dp, height = 54.dp),
                shape = MaterialTheme.shapes.small
            ) {
                Text(text = "Войти")
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun LoginPreview() {
    StudentAppTheme {
        LoginScreen({}, {})
    }
}