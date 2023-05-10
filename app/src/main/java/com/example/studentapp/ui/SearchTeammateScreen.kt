package com.example.studentapp.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
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
import com.example.studentapp.ui.search.TagItem
import com.example.studentapp.ui.theme.Red
import com.example.studentapp.ui.theme.StudentAppTheme

object SearchTeammateScreen : NavigationDestination {
    override val route: String = "SearchTeammate"
    const val projectId = "projectId"
    val routeWithArgs: String = "${route}/{$projectId}"
}

@OptIn(ExperimentalComposeUiApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SearchTeammateScreen(
    onCreateTeammate: () -> Unit,
    onNavigateBack: () -> Unit,
    name: String,
    description: String,
    tags: List<String>,
    onTagsChanged: (List<String>) -> Unit,
    onNameChanged: (String) -> Unit,
    onDescriptionChanged: (String) -> Unit,
    contentPadding: PaddingValues = PaddingValues()
) {
    Scaffold(topBar = { TopBar(onNavigateBack = { onNavigateBack() }) }) {
        val keyboardController = LocalSoftwareKeyboardController.current
        val focusRequester2 = remember { FocusRequester() }
        val focusRequester3 = remember { FocusRequester() }
        Box(Modifier.fillMaxSize()) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .fillMaxHeight()
                    .padding(bottom = 300.dp)
            ) {
                Text(text = "Кого вы хотите найти?", style = MaterialTheme.typography.h3)
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
                    keyboardActions = KeyboardActions(onNext = { focusRequester2.requestFocus() }),
                    label = { Text(text = "Кратко назовите должность") },
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
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                    keyboardActions = KeyboardActions(onNext = { focusRequester3.requestFocus() }),
                    label = { Text(text = "Опишите  кандидата в команду") },
                    modifier = Modifier
                        .height(160.dp)
                        .fillMaxWidth()
                        .padding(horizontal = 17.dp)
                        .focusRequester(focusRequester2),
                    shape = RoundedCornerShape(8.dp),
                )
                TextField(
                    singleLine = false,
                    value = tags.joinToString(separator = " "),
                    onValueChange = { onTagsChanged(it.split(" ")) },
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color(0xFFFBECFF),
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent
                    ),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(onDone = {
                        keyboardController?.hide()
                        onCreateTeammate()
                    }),
                    label = { Text(text = "Основные навыки через пробел") },
                    modifier = Modifier
                        .height(160.dp)
                        .fillMaxWidth()
                        .padding(horizontal = 17.dp)
                        .padding(top = 18.dp)
                        .focusRequester(focusRequester3),
                    shape = RoundedCornerShape(8.dp)
                )
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.padding(top = 13.dp, end = 17.dp, start = 17.dp)
                ) {
                    items(tags) { tag ->
                        TagItem(tag)
                    }
                }
            }
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom
            ) {
                ExtendedFloatingActionButton(
                    text = {
                        Text(
                            text = "Создать вакансию",
                            style = MaterialTheme.typography.button
                        )
                    },
                    onClick = { onCreateTeammate() },
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

@Preview(showBackground = true)
@Composable
fun SearchTeammatePreview() {
    StudentAppTheme {
        SearchTeammateScreen(
            onCreateTeammate = {},
            onNavigateBack = {},
            name = "name",
            description = "description",
            onDescriptionChanged = {},
            onTagsChanged = {},
            onNameChanged = {},
            tags = listOf("sdk", "sjkow", "sas;lw")
        )
    }
}