package com.example.studentapp.ui.message

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import com.example.studentapp.R
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.studentapp.ui.navigation.NavigationDestination
import com.example.studentapp.ui.theme.Red
import com.example.studentapp.ui.theme.StudentAppTheme

object ChattingScreen : NavigationDestination {
    override val route: String = "ChatingScreen"
}

@Composable
fun MessageTopBar(
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
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
                Image(
                    painter = painterResource(id = R.drawable.avatar),
                    contentDescription = null,
                    modifier = Modifier.size(29.dp)
                )
                Text(
                    text = "Дмитриев Максим",
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
                    DropdownMenu(
                        expanded = menuExpanded,
                        onDismissRequest = { menuExpanded = false }
                    ) {
                        Text(
                            text = "Принять в команду",
                            fontSize = 18.sp,
                            modifier = Modifier
                                .padding(10.dp)
                                .clickable { menuExpanded = false })
                    }
                }
            }
        }
    }
}

@Composable
fun ChattingScreen(onNavigateBack: () -> Unit, contentPadding: PaddingValues = PaddingValues()) {
    Scaffold(
        topBar = { MessageTopBar(onNavigateBack = { onNavigateBack() }) }
    ) {
        LazyColumn(
            modifier = Modifier.padding(top = 15.dp, end = 13.dp, start = 13.dp),
            contentPadding = contentPadding
        ) {
            items(
                listOf(
                    Message(
                        from = false,
                        text = "Здравствуйте! Очень понравилась идея вашего приложения, хотел бы вступить в команду. Не было большого опыта в разработке подобных приложений, но зато я легко обучаем и не доставлю вам лишних хлопот.",
                        time = "12:50"
                    ),
                    Message(from = true, text = "Вы отлично нам подходите!", time = "13:00"),
                    Message(
                        from = false,
                        text = "Боже мой! Я безумно счастлив! Я буквально с самого рождания мечтал попасть к вам в команду, не могу поверить своим глазам...",
                        time = "13:01"
                    ),
                    Message(from = true, text = "Мы передумали.", time = "15:10"),
                    Message(
                        from = false,
                        text = "Максим, на самом деле я люблю вас! Я хотел попасть в команию только ради вас. Пожалуйста, не отвергайте меня, я хочу быть с вами и провести с вами всю оставшуюся жизнь",
                        time = "15:11"
                    ),
                    Message(
                        from = true,
                        text = "Я что для себя по вашему точки в конце каждого предложения ставлю.",
                        time = "15:20"
                    )
                )
            ) { message ->
                if (!message.from)
                    MyMessage(text = message.text, time = message.time)
                else
                    FromMessage(text = message.text, time = message.time)
            }
        }
    }
}

@Composable
fun SendMessage(onClickSendButton: () -> Unit) {
    var message by remember { mutableStateOf("") }
    TextField(
        singleLine = true,
        value = message,
        onValueChange = { message = it },
        trailingIcon = {
            IconButton(onClick = {}) {
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
        keyboardActions = KeyboardActions(onSearch = { onClickSendButton() }),
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
        Text(text = time, style = MaterialTheme.typography.subtitle2)
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
        Text(text = time, style = MaterialTheme.typography.subtitle2)
    }
}

@Preview(showBackground = true)
@Composable
fun MessageScreenPreview() {
    StudentAppTheme {
        ChattingScreen(onNavigateBack = {})
    }
}

data class Message(
    val from: Boolean,
    val text: String,
    val time: String
)