package com.example.studentapp.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.studentapp.R
import com.example.studentapp.data.PageType
import com.example.studentapp.data.navigationItemContentList
import com.example.studentapp.ui.navigation.NavigationDestination
import com.example.studentapp.ui.theme.Red
import com.example.studentapp.ui.theme.StudentAppTheme

object EndScreen : NavigationDestination {
    override val route: String = "EndScreen"
}

@Composable
fun EndScreen(
    onClickEnd: () -> Unit = {},
    onNavigateBack: () -> Unit = {},
    contentPadding: PaddingValues = PaddingValues()
) {
    Scaffold(topBar = { TopBar(onNavigateBack = { onNavigateBack() }) }) {
        Box() {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(24.dp),
                contentPadding = PaddingValues(top = 15.dp, bottom = contentPadding.calculateBottomPadding() + 75.dp)
            ) {
                items(listOf("Алексей Некифоров", "Роман Новиков")) { name ->
                    FeedbackCard(name = name)
                }
                item {
                    Button(
                        onClick = { /*TODO*/ },
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color(0xFFF2F2F2),
                            contentColor = Color(0xFF595959)
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 13.dp, end = 13.dp, bottom = 25.dp)
                    ) {
                        Row(modifier = Modifier.padding(vertical = 10.dp)) {
                            Text(
                                text = "Загрузите фото",
                                style = MaterialTheme.typography.subtitle1
                            )
                            Spacer(modifier = Modifier.weight(1f))
                            Image(
                                painter = painterResource(id = R.drawable.upload_icon),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(17.dp)
                                    .padding(top = 4.dp)
                            )
                        }
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
                            text = "Завершить проект",
                            style = MaterialTheme.typography.button
                        )
                    },
                    onClick = { onClickEnd() },
                    backgroundColor = Color(0xFF9378FF),
                    modifier = Modifier
                        .padding(bottom = contentPadding.calculateBottomPadding() + 15.dp)
                        .height(54.dp)
                        .width(263.dp)
                )
            }
        }
    }
}

@Composable
fun FeedbackCard(name: String) {
    var feedback by remember { mutableStateOf("") }
    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        Text(text = name, style = MaterialTheme.typography.h5)
        TextField(
            singleLine = true,
            value = feedback,
            onValueChange = { feedback = it },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color(0xFFF2F2F2),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
            label = {
                Text(
                    text = "Напишите отзыв", style = TextStyle(
                        fontWeight = FontWeight.Normal,
                        fontSize = 16.sp,
                        color = Color(0xFF595959),
                        fontFamily = Red
                    )
                )
            },
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .padding(vertical = 13.dp)
                .fillMaxWidth()
        )
        Text(
            text = "Оценка",
            style = MaterialTheme.typography.h6,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        Image(
            painter = painterResource(id = R.drawable.stars),
            contentDescription = null,
            alignment = Alignment.CenterStart,
            modifier = Modifier
                .width(100.dp)
                .height(16.dp)
        )
        Divider(
            thickness = 1.dp,
            modifier = Modifier.padding(top = 24.dp, start = 12.dp, end = 12.dp)
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun EndScreenPreview() {
    StudentAppTheme {
        EndScreen()
    }
}