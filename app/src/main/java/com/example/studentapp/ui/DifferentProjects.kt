package com.example.studentapp.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.studentapp.data.PageType
import com.example.studentapp.data.navigationItemContentList
import com.example.studentapp.ui.navigation.NavigationDestination
import com.example.studentapp.ui.theme.StudentAppTheme

object DifferentProjects : NavigationDestination {
    override val route: String = "DifferentProjects"
}

@Composable
fun DifferentProjects(onNavigateBack: () -> Unit, onClickProject: () -> Unit) {
    Scaffold(topBar = { TopBar(onNavigateBack = { onNavigateBack() }) }) {
        LazyColumn(
            contentPadding = PaddingValues(start = 8.dp, end = 8.dp, top = 10.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            item {
                Text(
                    text = "Активные проекты",
                    style = MaterialTheme.typography.h3,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
            items(
                listOf("Android-приложение для ориганизации мероприятий")
            ) { name ->
                ProjectCard(name = name, onClickProject = onClickProject)
            }
            item {
                Text(
                    text = "Завершённые проекты",
                    style = MaterialTheme.typography.h3,
                    modifier = Modifier.padding(start = 8.dp, top = 10.dp)
                )
            }
            items(
                listOf(
                    "Android-приложение для кофейни Stars Coffee",
                    "Сайт, хранящий коды различных цветов"
                )
            ) { name ->
                ProjectCard(name = name, onClickProject = onClickProject)
            }
        }
    }
}

@Composable
fun ProjectCard(name: String, onClickProject: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable { onClickProject() },
        shape = RoundedCornerShape(8.dp),
        elevation = 8.dp
    ) {
        Column(modifier = Modifier.padding(25.dp)) {
            Text(
                text = name,
                style = MaterialTheme.typography.h5,
                modifier = Modifier.padding(bottom = 9.dp)
            )
            Text(
                text = "Участник",
                style = MaterialTheme.typography.h4
            )
        }

    }
}

@Preview(showBackground = true)
@Composable
fun DifferentProjectsScreen() {
    StudentAppTheme {
        DifferentProjects(onNavigateBack = {}, onClickProject = {})
    }
}