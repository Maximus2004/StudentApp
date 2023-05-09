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

object SearchTeammateScreen : NavigationDestination {
    override val route: String = "SearchTeammate"
}

@OptIn(ExperimentalComposeUiApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SearchTeammateScreen(onCreateTeammate: () -> Unit, onNavigateBack: () -> Unit) {
    Scaffold(
        topBar = { TopBar(onNavigateBack = { onNavigateBack() }) },
        bottomBar = {
            BottomNavigationBar(
                currentTab = PageType.Search,
                onTabPressed = { },
                navigationItemContentList = navigationItemContentList
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                text = {
                    Text(
                        text = "Опубликовать",
                        style = MaterialTheme.typography.button
                    )
                },
                onClick = { onCreateTeammate() },
                backgroundColor = Color(0xFF9378FF),
                modifier = Modifier
                    .height(54.dp)
                    .width(263.dp)
            )
        },
        floatingActionButtonPosition = FabPosition.Center
    ) {
        var name by remember { mutableStateOf("") }
        var description by remember { mutableStateOf("") }
        var tags by remember { mutableStateOf("") }
        val keyboardController = LocalSoftwareKeyboardController.current
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Кого вы хотите найти?", style = MaterialTheme.typography.h3)
            TextField(
                singleLine = true,
                value = name,
                onValueChange = { name = it },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color(0xFFFBECFF),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onSearch = { onCreateTeammate() }),
                label = { Text(text = "Кратко назовите должность") },
                modifier = Modifier.padding(18.dp).height(56.dp).fillMaxWidth(),
                shape = RoundedCornerShape(8.dp)
            )
            TextField(
                singleLine = false,
                value = description,
                onValueChange = { description = it },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color(0xFFFBECFF),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onSearch = { onCreateTeammate() }),
                label = { Text(text = "Опишите  кандидата в команду") },
                modifier = Modifier
                    .height(160.dp)
                    .fillMaxWidth()
                    .padding(horizontal = 17.dp),
                shape = RoundedCornerShape(8.dp)
            )
            TextField(
                singleLine = false,
                value = tags,
                onValueChange = { tags = it },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color(0xFFFBECFF),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = {
                    keyboardController?.hide()
                    onCreateTeammate() }),
                label = { Text(text = "Основные навыки через пробел") },
                modifier = Modifier
                    .height(160.dp)
                    .fillMaxWidth()
                    .padding(horizontal = 17.dp)
                    .padding(top = 18.dp),
                shape = RoundedCornerShape(8.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SearchTeammatePreview() {
    StudentAppTheme {
        SearchTeammateScreen(onCreateTeammate = {}, onNavigateBack = {})
    }
}