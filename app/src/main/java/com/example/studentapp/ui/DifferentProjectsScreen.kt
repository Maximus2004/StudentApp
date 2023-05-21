package com.example.studentapp.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExtendedFloatingActionButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.studentapp.data.Project
import com.example.studentapp.ui.navigation.NavigationDestination

object DifferentProjects : NavigationDestination {
    override val route: String = "DifferentProjects"
    const val isAlien = "isAlien"
    val routeWithArgs: String = "${route}/{$isAlien}"
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun DifferentProjectsScreen(
    onNavigateBack: () -> Unit = {},
    onClickActiveLeaderProject: (Project) -> Unit = {},
    onClickActiveSubordinateProject: (Project) -> Unit = {},
    onClickNotActiveProject: (Project) -> Unit = {},
    leaderProjects: HashMap<Project, Boolean>,
    subordinateProjects: HashMap<Project, Boolean>,
    onClickCreateTeam: () -> Unit = {},
    isShowingCreationButton: Boolean,
    contentPadding: PaddingValues = PaddingValues()
) {
    Scaffold(topBar = { TopBar(onNavigateBack = { onNavigateBack() }) }) {
        Box {
            LazyColumn(
                contentPadding = PaddingValues(start = 8.dp, end = 8.dp, top = 10.dp, bottom = contentPadding.calculateBottomPadding() + 77.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                item {
                    if (leaderProjects.isEmpty() && subordinateProjects.isEmpty()) {
                        Box(modifier = Modifier.fillMaxSize()) {
                            Text(text = "Здесь пока нет проектов",
                                modifier = Modifier.align(Alignment.Center).padding(10.dp),
                                textAlign = TextAlign.Center)
                        }
                    }
                }
                items(leaderProjects.toList()) { project ->
                    ProjectCard(
                        name = project.first.name,
                        onClickProject = {
                            if (project.second) {
                                if (isShowingCreationButton) onClickActiveLeaderProject(project.first)
                                else onClickActiveSubordinateProject(project.first)
                            }
                            else
                                onClickNotActiveProject(project.first)
                        },
                        isLeader = true,
                        isActive = project.second,
                        projectId = project.first.id
                    )
                }
                items(subordinateProjects.toList()) { project ->
                    ProjectCard(
                        name = project.first.name,
                        onClickProject = {
                            if (project.second)
                                onClickActiveSubordinateProject(project.first)
                            else
                                onClickNotActiveProject(project.first)
                        },
                        isLeader = false,
                        isActive = project.second,
                        projectId = project.first.id
                    )
                }
            }
            if (isShowingCreationButton) {
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
                        onClick = { onClickCreateTeam() },
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
}