package com.example.studentapp.ui.profile

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExtendedFloatingActionButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.example.studentapp.ui.TopBar
import com.example.studentapp.ui.navigation.NavigationDestination

object ProjectCreationScreen : NavigationDestination {
    override val route: String = "ProjectCreation"
}

@OptIn(ExperimentalComposeUiApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ProjectCreationScreen(
    onNameChanged: (String) -> Unit,
    onDescriptionChanged: (String) -> Unit,
    name: String,
    description: String,
    onCreateProject: () -> Unit,
    onNavigateBack: () -> Unit,
    contentPadding: PaddingValues = PaddingValues(),
    isKeyboardOpen: Boolean = false
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusRequester3 = remember { FocusRequester() }
    Scaffold(topBar = { TopBar(onNavigateBack = { onNavigateBack() }) }) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(bottom = if (isKeyboardOpen) 240.dp else 0.dp)
            ) {
                Text(text = "Создание проекта", style = MaterialTheme.typography.h3)
                TextField(
                    singleLine = true,
                    value = name,
                    onValueChange = { onNameChanged(it) },
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color(0xFFFBECFF),
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent
                    ),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                    keyboardActions = KeyboardActions(onNext = { focusRequester3.requestFocus() }),
                    label = { Text(text = "Введите название проекта") },
                    modifier = Modifier
                        .padding(18.dp)
                        .height(56.dp)
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp)
                )
                TextField(
                    singleLine = false,
                    value = description,
                    onValueChange = { onDescriptionChanged(it) },
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color(0xFFFBECFF),
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent
                    ),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() }),
                    label = { Text(text = "Опишите своё будущее детище") },
                    modifier = Modifier
                        .height(160.dp)
                        .fillMaxWidth()
                        .padding(horizontal = 17.dp)
                        .focusRequester(focusRequester3),
                    shape = RoundedCornerShape(8.dp)
                )
            }
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom
            ) {
                ExtendedFloatingActionButton(
                    text = {
                        Text(
                            text = "Создать проект",
                            style = MaterialTheme.typography.button
                        )
                    },
                    onClick = { onCreateProject() },
                    backgroundColor = Color(0xFF9378FF),
                    modifier = Modifier
                        .padding(bottom = 15.dp + contentPadding.calculateBottomPadding())
                        .height(54.dp)
                        .width(263.dp),
                )
            }
        }
    }
}