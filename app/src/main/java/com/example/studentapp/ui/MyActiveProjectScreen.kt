package com.example.studentapp.ui

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.studentapp.R
import com.example.studentapp.ui.theme.StudentAppTheme

@Composable
fun MyActiveProjectScreen() {
    Scaffold(
        topBar = { TopBar(onNavigateBack = {}) },
        floatingActionButton = {
            Row() {
                ExtendedFloatingActionButton(
                    text = {
                        Text(
                            text = "Завершить",
                            style = MaterialTheme.typography.button
                        )
                    },
                    onClick = { /*TODO*/ },
                    backgroundColor = Color(0xFF99879D),
                    modifier = Modifier
                        .height(54.dp)
                        .width(153.dp),
                    shape = RoundedCornerShape(topStart = 40.dp, topEnd = 0.dp, bottomEnd = 0.dp, bottomStart = 40.dp)
                )
                ExtendedFloatingActionButton(
                    text = {
                        Text(
                            text = "Набрать",
                            style = MaterialTheme.typography.button
                        )
                    },
                    onClick = { /*TODO*/ },
                    backgroundColor = Color(0xFF9378FF),
                    modifier = Modifier
                        .height(54.dp)
                        .width(153.dp),
                    shape = RoundedCornerShape(topStart = 0.dp, topEnd = 40.dp, bottomEnd = 40.dp, bottomStart = 0.dp)
                )
            }
        },
        floatingActionButtonPosition = FabPosition.Center
    ) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            item {
                Text(
                    text = "Опубликовано 3 дня назад",
                    style = MaterialTheme.typography.subtitle2,
                    modifier = Modifier.padding(start = 24.dp, end = 24.dp, top = 24.dp)
                )
                Text(
                    text = "Android-приложение для организации мероприятий",
                    style = MaterialTheme.typography.h3,
                    modifier = Modifier.padding(start = 25.dp, top = 4.dp, end = 24.dp)
                )
                Text(
                    text = "Мы создаём приложение, которое поможет людям в организации самых различных мероприятий, начиная от гей-парадов и заканчивая научными конференциями. Собираем команду инициативных и обучаемый ребят, которые будут готовы активно работать над общей идеей.",
                    style = MaterialTheme.typography.h4,
                    modifier = Modifier.padding(
                        start = 24.dp,
                        top = 16.dp,
                        end = 24.dp,
                        bottom = 16.dp
                    ),
                    lineHeight = 27.sp
                )

            }
            items(
                listOf(
                    Member("Максим Дмитриев", true, R.drawable.avatar)
                )
            ) { member ->
                MemberCard(member, onProfileClick = {})
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MyActivePreview() {
    StudentAppTheme {
        MyActiveProjectScreen()
    }
}