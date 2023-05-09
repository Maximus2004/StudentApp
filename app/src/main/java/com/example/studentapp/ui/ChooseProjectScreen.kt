package com.example.studentapp.ui

import android.annotation.SuppressLint
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.studentapp.data.Project
import com.example.studentapp.ui.navigation.NavigationDestination
import com.example.studentapp.ui.theme.Red
import com.example.studentapp.ui.theme.StudentAppTheme

object ChooseProjectScreen : NavigationDestination {
    override val route: String = "ChooseProjectScreen"
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ChooseProjectScreen(
    onNavigateBack: () -> Unit,
    onClickProject: () -> Unit,
    onCreateProject: () -> Unit,
    leaderProjects: List<Project>,
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
                items(leaderProjects) { project ->
                    if (project.isActive)
                        ProjectCard(
                            name = project.name,
                            onClickProject = onClickProject,
                            isActive = true,
                            isLeader = true
                        )
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