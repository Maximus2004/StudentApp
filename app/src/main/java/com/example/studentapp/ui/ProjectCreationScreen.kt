package com.example.studentapp.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.studentapp.data.PageType
import com.example.studentapp.data.navigationItemContentList
import com.example.studentapp.ui.navigation.NavigationDestination
import com.example.studentapp.ui.theme.Red
import com.example.studentapp.ui.theme.StudentAppTheme
import kotlinx.coroutines.launch

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
    onCreateTeam: () -> Unit,
    onNavigateBack: () -> Unit,
    contentPadding: PaddingValues = PaddingValues()
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    Scaffold(topBar = { TopBar(onNavigateBack = { onNavigateBack() }) }) {
        Box() {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
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
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(onSearch = { onCreateTeam() }),
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
                    keyboardActions = KeyboardActions(onDone = {
                        keyboardController?.hide()
                        onCreateTeam()
                    }),
                    label = { Text(text = "Опишите своё будущее детище") },
                    modifier = Modifier
                        .height(160.dp)
                        .fillMaxWidth()
                        .padding(horizontal = 17.dp),
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
                    onClick = { onCreateTeam() },
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