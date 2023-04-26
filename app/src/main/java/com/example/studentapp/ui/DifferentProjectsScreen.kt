package com.example.studentapp.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.studentapp.data.Project
import com.example.studentapp.ui.navigation.NavigationDestination
import com.example.studentapp.ui.profile.ProfileUiState
import com.example.studentapp.ui.theme.StudentAppTheme

object DifferentProjects : NavigationDestination {
    override val route: String = "DifferentProjects"
}

@Composable
fun DifferentProjectsScreen(
    onNavigateBack: () -> Unit = {},
    onClickActiveLeaderProject: (Int) -> Unit = {},
    onClickActiveSubordinateProject: (Int) -> Unit = {},
    onClickNotActiveProject: (Int) -> Unit = {},
    leaderProjects: List<Project>,
    subordinateProjects: List<Project>
) {
    Scaffold(topBar = { TopBar(onNavigateBack = { onNavigateBack() }) }) {
        LazyColumn(
            contentPadding = PaddingValues(start = 8.dp, end = 8.dp, top = 10.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(leaderProjects) { project ->
                ProjectCard(
                    name = project.name,
                    onClickProject = {
                        if (project.isActive)
                            onClickActiveLeaderProject(project.id)
                        else
                            onClickNotActiveProject(project.id)
                    },
                    isLeader = true
                )
            }
            items(subordinateProjects) { project ->
                ProjectCard(
                    name = project.name,
                    onClickProject = {
                        if (project.isActive)
                            onClickActiveSubordinateProject(project.id)
                        else
                            onClickNotActiveProject(project.id)
                    },
                    isLeader = false
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DifferentProjectsScreenPreview() {
    StudentAppTheme {
        DifferentProjectsScreen(leaderProjects = listOf(), subordinateProjects = listOf())
    }
}