package com.example.studentapp.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.studentapp.data.Project
import com.example.studentapp.ui.navigation.NavigationDestination

object ChooseProjectScreen : NavigationDestination {
    override val route: String = "ChooseProjectScreen"
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ChooseProjectScreen(
    onNavigateBack: () -> Unit,
    onClickProject: (String) -> Unit,
    onCreateProject: () -> Unit,
    leaderProjects: HashMap<Project, Boolean>,
    contentPadding: PaddingValues = PaddingValues()
) {
    Scaffold(topBar = { TopBar(onNavigateBack = { onNavigateBack() }) }) {
        Box() {
            LazyColumn(
                contentPadding = PaddingValues(start = 8.dp, end = 8.dp, top = 3.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                item {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Выберите проект",
                            style = MaterialTheme.typography.h3,
                            modifier = Modifier.padding(bottom = 4.dp)
                        )
                    }
                }
                item {
                    if (leaderProjects.isEmpty()) {
                        Box(modifier = Modifier.fillMaxSize()) {
                            Text(
                                text = "Сейчас у вас нет собственных активных проектов",
                                modifier = Modifier.align(Alignment.Center).padding(10.dp),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
                items(leaderProjects.toList()) { project ->
                    Box(modifier = Modifier.fillMaxSize()) {
                        Text(
                            text = "Сейчас у вас нет собственных активных проектов",
                            modifier = Modifier.align(Alignment.Center).padding(10.dp),
                            textAlign = TextAlign.Center
                        )
                        if (project.second)
                            ProjectCard(
                                name = project.first.name,
                                projectId = project.first.id,
                                onClickProject = onClickProject,
                                isActive = true,
                                isLeader = true
                            )
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
                            text = "Создать проект",
                            style = MaterialTheme.typography.button
                        )
                    },
                    onClick = { onCreateProject() },
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