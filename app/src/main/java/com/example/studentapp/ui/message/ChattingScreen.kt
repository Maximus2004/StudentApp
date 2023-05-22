package com.example.studentapp.ui.message

import android.annotation.SuppressLint
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import com.example.studentapp.R
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.studentapp.data.Message
import com.example.studentapp.ui.navigation.NavigationDestination
import com.example.studentapp.ui.theme.Red
import com.example.studentapp.ui.theme.StudentAppTheme

@Composable
fun MessageTopBar(
    name: String,
    surname: String,
    avatar: String,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    projects: HashMap<String, String>,
    onClickAcceptButton: (String) -> Unit
) {
    var menuExpanded by remember { mutableStateOf(false) }
    TopAppBar(
        elevation = 8.dp,
        backgroundColor = Color(0xFFFFFFFF),
        modifier = Modifier.height(132.dp)
    ) {
        Column() {
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(top = 6.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onNavigateBack) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        modifier = Modifier.size(21.dp),
                        tint = Color(0xFF99879D)
                    )
                }
                Text(
                    text = "Назад",
                    style = TextStyle(
                        fontWeight = FontWeight.Medium,
                        fontSize = 18.sp,
                        color = Color(0xFF99879D)
                    )
                )
            }
            Row(
                modifier = Modifier.padding(start = 15.dp, top = 7.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(Uri.parse(avatar))
                        .crossfade(true)
                        .build(),
                    contentScale = ContentScale.Crop,
                    contentDescription = null,
                    modifier = Modifier
                        .size(29.dp)
                        .clip(CircleShape)
                )
                Text(
                    text = "$name $surname",
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 25.sp,
                        color = Color(0xFF120E21)
                    ),
                    modifier = Modifier.padding(start = 13.dp)
                )
                Spacer(modifier = Modifier.weight(1f))
                Box {
                    IconButton(
                        onClick = { menuExpanded = !menuExpanded },
                        modifier = Modifier.padding(top = 5.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = null
                        )
                    }
                    if (projects.isNotEmpty()) {
                        DropdownMenu(
                            expanded = menuExpanded,
                            onDismissRequest = { menuExpanded = false }
                        ) {
                            projects.toList().forEach { project ->
                                DropdownMenuItem(onClick = {
                                    menuExpanded = false
                                    onClickAcceptButton(project.first)
                                }) {
                                    Text(
                                        text = "Принять на должность ${project.second}",
                                        fontSize = 18.sp,
                                        modifier = Modifier.padding(4.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ChattingScreen(
    chatList: List<Message>,
    currentUserId: String,
    onNavigateBack: () -> Unit,
    contentPadding: PaddingValues = PaddingValues(),
    currentUserName: String,
    currentUserSurname: String,
    currentUserAvatar: String,
    onClickAcceptButton: (String) -> Unit,
    projects: HashMap<String, String>
) {
    Scaffold(
        topBar = {
            MessageTopBar(
                onNavigateBack = { onNavigateBack() },
                name = currentUserName,
                surname = currentUserSurname,
                avatar = currentUserAvatar,
                onClickAcceptButton = onClickAcceptButton,
                projects = projects
            )
        },
    ) {
        LazyColumn(
            modifier = Modifier.padding(top = 15.dp, end = 13.dp, start = 13.dp),
            contentPadding = contentPadding
        ) {
            items(chatList) { message ->
                if (currentUserId == message.send)
                    MyMessage(text = message.text, time = message.time)
                else
                    FromMessage(text = message.text, time = message.time)
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SendMessage(onClickSendButton: (String) -> Unit) {
    var message by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current
    TextField(
        singleLine = true,
        value = message,
        onValueChange = { message = it },
        trailingIcon = {
            IconButton(onClick = {
                onClickSendButton(message)
                message = ""
            }) {
                Icon(
                    imageVector = Icons.Default.Send,
                    contentDescription = null,
                    modifier = Modifier.size(31.dp),
                    tint = Color(0xFF99879D)
                )
            }
        },
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color(0xFFFFFFFF),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        ),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send),
        keyboardActions = KeyboardActions(onSend = {
            keyboardController?.hide()
            onClickSendButton(message)
            message = ""
        }),
        modifier = Modifier
            .fillMaxWidth()
            .height(63.dp)
    )

}

@Composable
fun FromMessage(text: String, time: String) {
    Column(horizontalAlignment = Alignment.Start, modifier = Modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier
                .clip(
                    shape = RoundedCornerShape(
                        topStart = 8.dp,
                        topEnd = 8.dp,
                        bottomStart = 0.dp,
                        bottomEnd = 8.dp
                    )
                )
                .background(Color(0xFF9378FF))
                .width(181.dp)
        ) {
            Text(
                text = text,
                style = TextStyle(
                    fontWeight = FontWeight.Normal,
                    fontSize = 13.sp,
                    color = Color(0xFFFFFFFF),
                    fontFamily = Red
                ),
                modifier = Modifier.padding(12.dp),
                lineHeight = 19.sp
            )
        }
        Text(text = time.dropLast(3), style = MaterialTheme.typography.subtitle2)
    }
}

@Composable
fun MyMessage(text: String, time: String, modifier: Modifier = Modifier) {
    Column(horizontalAlignment = Alignment.End, modifier = Modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier
                .clip(
                    shape = RoundedCornerShape(
                        topStart = 8.dp,
                        topEnd = 8.dp,
                        bottomStart = 8.dp,
                        bottomEnd = 0.dp
                    )
                )
                .background(Color(0xFFCABDFD))
                .width(181.dp)
        ) {
            Text(
                text = text,
                style = TextStyle(
                    fontWeight = FontWeight.Normal,
                    fontSize = 13.sp,
                    color = Color(0xFFFFFFFF),
                    fontFamily = Red
                ),
                modifier = Modifier.padding(12.dp),
                lineHeight = 19.sp
            )
        }
        Text(text = time.dropLast(3), style = MaterialTheme.typography.subtitle2)
    }
}

@Preview(showBackground = true)
@Composable
fun MessageScreenPreview() {
    StudentAppTheme {
        ChattingScreen(
            onNavigateBack = {},
            chatList = listOf(),
            currentUserId = "",
            currentUserSurname = "Дмитриев",
            currentUserName = "Максим",
            currentUserAvatar = "",
            onClickAcceptButton = {},
            projects = hashMapOf()
        )
    }
}