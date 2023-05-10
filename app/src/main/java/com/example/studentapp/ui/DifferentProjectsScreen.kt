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
import androidx.compose.ui.unit.dp
import com.example.studentapp.data.Project
import com.example.studentapp.ui.navigation.NavigationDestination

object DifferentProjects : NavigationDestination {
    override val route: String = "DifferentProjects"
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun DifferentProjectsScreen(
    onNavigateBack: () -> Unit = {},
    onClickActiveLeaderProject: (String) -> Unit = {},
    onClickActiveSubordinateProject: (String) -> Unit = {},
    onClickNotActiveProject: (String) -> Unit = {},
    leaderProjects: MutableList<Project>,
    subordinateProjects: MutableList<Project>,
    onClickCreateTeam: () -> Unit = {},
    isShowingCreationButton: Boolean = true,
    contentPadding: PaddingValues = PaddingValues()
) {
    Scaffold(topBar = { TopBar(onNavigateBack = { onNavigateBack() }) }) {
        Box {
            LazyColumn(
                contentPadding = PaddingValues(start = 8.dp, end = 8.dp, top = 10.dp, bottom = contentPadding.calculateBottomPadding() + 77.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(leaderProjects) { project ->
                    ProjectCard(
                        name = project.name,
                        onClickProject = {
                            if (project.active) {
                                if (isShowingCreationButton) onClickActiveLeaderProject(project.id)
                                else onClickActiveSubordinateProject(project.id)
                            }
                            else
                                onClickNotActiveProject(project.id)
                        },
                        isLeader = true,
                        isActive = project.active,
                        projectId = project.id
                    )
                }
                items(subordinateProjects) { project ->
                    ProjectCard(
                        name = project.name,
                        onClickProject = {
                            if (project.active)
                                onClickActiveSubordinateProject(project.id)
                            else
                                onClickNotActiveProject(project.id)
                        },
                        isLeader = false,
                        isActive = project.active,
                        projectId = project.id
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