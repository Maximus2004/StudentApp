package com.example.studentapp.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.studentapp.data.photos
import com.example.studentapp.ui.theme.StudentAppTheme
import androidx.compose.material.*
import com.example.studentapp.ui.navigation.NavigationDestination
import com.example.studentapp.ui.profile.InfoCard
import com.example.studentapp.ui.profile.PhotoItem
import com.example.studentapp.ui.profile.UserCard
import com.example.studentapp.ui.profile.header

object AlienScreen : NavigationDestination {
    override val route: String = "AlienScreen"
}

@Composable
fun AlienProfileScreen(onClickShowProjects: () -> Unit, contentPadding: PaddingValues = PaddingValues(), onNavigateBack: () -> Unit) {
    Scaffold(topBar = { TopBar(onNavigateBack = { onNavigateBack() }) }) {
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 140.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.padding(horizontal = 18.dp),
            contentPadding = PaddingValues(bottom = contentPadding.calculateBottomPadding() + 9.dp)
        ) {
            header {
                Column() {
                    UserCard(
                        name = "Головач Лена",
                        position = "Дизайнер",
                        modifier = Modifier.padding(bottom = 25.dp, top = 6.dp)
                    )
                    Text(text = "Описание", style = MaterialTheme.typography.h5)
                    Text(
                        text = "Привет, меня зовут Дастин, я из Дублина. Опыт работы в районе 4 лет. Буду рад сотрудничесту с вами, всегда вовремя выполняю работу, очень отвественный и вообще я молодец",
                        style = MaterialTheme.typography.h4,
                        modifier = Modifier.padding(top = 12.dp)
                    )
                    InfoCard(modifier = Modifier.padding(top = 11.dp), onClickShowProjects = onClickShowProjects)
                    Text(text = "Портфолио", style = MaterialTheme.typography.h5)
                }
            }
            items(photos) { photo ->
                PhotoItem(photo, modifier = Modifier.aspectRatio(1.5f))
            }
        }
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = false)
fun AlienPreview() {
    StudentAppTheme {
        AlienProfileScreen(onClickShowProjects = {}, onNavigateBack = {})
    }
}