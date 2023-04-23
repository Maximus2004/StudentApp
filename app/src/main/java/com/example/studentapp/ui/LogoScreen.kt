package com.example.studentapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.studentapp.ui.navigation.NavigationDestination
import com.example.studentapp.ui.theme.StudentAppTheme

object LogoScreen : NavigationDestination {
    override val route = "LogoScreen"
}

@Composable
fun LogoScreen(onClickAuthButton: () -> Unit, onClickRegisterButton: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.background_bubbles),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = "Teams",
                style = MaterialTheme.typography.h1,
                modifier = Modifier
                    .padding(top = 218.dp)
                    .align(Alignment.CenterHorizontally)
            )

            Button(
                onClick = {
                    onClickRegisterButton()
                },
                modifier = Modifier
                    .padding(top = 257.dp)
                    .size(width = 263.dp, height = 54.dp),
                shape = MaterialTheme.shapes.small
            ) {
                Text(text = "Регистрация")
            }
            Row(modifier = Modifier.padding(top = 37.dp)) {
                Text(text = "Уже есть аккаунт? ", style = MaterialTheme.typography.body1)
                Text(
                    text = "Войти",
                    style = MaterialTheme.typography.body2,
                    modifier = Modifier.clickable {
                        onClickAuthButton()
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LogoScreenPreview() {
    StudentAppTheme {
        LogoScreen({}, {})
    }
}