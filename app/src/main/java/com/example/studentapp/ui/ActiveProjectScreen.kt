package com.example.studentapp.ui

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.example.studentapp.ui.navigation.NavigationDestination
import com.example.studentapp.ui.theme.StudentAppTheme

object ActiveProjectScreen : NavigationDestination {
    override val route: String = "ActiveProjectScreen"
}

@Composable
fun ActiveProjectScreen(
    contentPadding: PaddingValues = PaddingValues(),
    onReplyButton: () -> Unit,
    onClickProfile: () -> Unit,
    onNavigateBack: () -> Unit
) {
    Scaffold(topBar = { TopBar(onNavigateBack = { onNavigateBack() }) }) {
        Box() {
            LazyColumn(modifier = Modifier.fillMaxSize(), contentPadding = contentPadding) {
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
                        Member("Максим Дмитриев", true, R.drawable.avatar),
                        Member("Майкл Джексон", false, R.drawable.avatar),
                        Member("Канье Уэст", false, R.drawable.avatar)
                    )
                ) { member ->
                    MemberCard(member, onProfileClick = { onClickProfile() })
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
                            text = "Откликнуться",
                            style = MaterialTheme.typography.button
                        )
                    },
                    onClick = { onReplyButton() },
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
fun ActivePreview() {
    StudentAppTheme {
        ActiveProjectScreen(onReplyButton = {}, onClickProfile = {}, onNavigateBack = {})
    }
}

data class Member(
    val name: String,
    val role: Boolean,
    @DrawableRes
    val avatar: Int
)