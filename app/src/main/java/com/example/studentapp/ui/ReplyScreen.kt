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
import com.example.studentapp.ui.search.DetailTeammateScreen
import com.example.studentapp.ui.theme.Red
import com.example.studentapp.ui.theme.StudentAppTheme

object ReplyScreen : NavigationDestination {
    override val route: String = "ReplyScreen"
}

@OptIn(ExperimentalComposeUiApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ReplyScreen(
    onNavigateBack: () -> Unit,
    onClickSendButton: (String) -> Unit,
    teamName: String,
    contentPadding: PaddingValues = PaddingValues()
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    Scaffold(
        topBar = { TopBar(onNavigateBack = { onNavigateBack() }) },
        floatingActionButtonPosition = FabPosition.Center
    ) {
        var description by remember { mutableStateOf("") }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = teamName,
                style = MaterialTheme.typography.h3,
                modifier = Modifier.padding(40.dp)
            )
            TextField(
                singleLine = false,
                value = description,
                onValueChange = { description = it },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color(0xFFF2F2F2),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() }),
                modifier = Modifier
                    .height(200.dp)
                    .fillMaxWidth()
                    .padding(horizontal = 17.dp),
                shape = RoundedCornerShape(8.dp),
                label = { Text(text = "Отправьте сообщение руководителю") }
            )
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom
            ) {
                ExtendedFloatingActionButton(
                    text = {
                        Text(
                            text = "Откликнуться",
                            style = MaterialTheme.typography.button
                        )
                    },
                    onClick = { onClickSendButton(description) },
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
fun ReplyScreenPreview() {
    StudentAppTheme {
        ReplyScreen(
            onNavigateBack = {},
            onClickSendButton = {},
            teamName = "Android-разработчик"
        )
    }
}