package com.example.studentapp.ui

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.studentapp.data.Project
import com.example.studentapp.data.User
import com.example.studentapp.data.projects
import com.example.studentapp.ui.home.TAG
import com.example.studentapp.ui.navigation.NavigationDestination
import com.example.studentapp.ui.theme.StudentAppTheme

object MyActiveProjectScreen : NavigationDestination {
    override val route = "MyActiveProjectScreen"
    const val projectId = "projectId"
    val routeWithArgs: String = "$route/{$projectId}"
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MyActiveProjectScreen(
    project: Project,
    onNavigateBack: () -> Unit,
    contentPadding: PaddingValues = PaddingValues(),
    onCollectPeople: () -> Unit = {},
    onEndProject: () -> Unit = {},
    users: List<User> = listOf()
) {
    Log.d(TAG, project.name)
    Scaffold(
        topBar = { TopBar(onNavigateBack = { onNavigateBack() }) },
    ) {
        Box() {
            AnimatedVisibility(visible = project.name.isNotEmpty()) {
                LazyColumn(modifier = Modifier.fillMaxSize(), contentPadding = PaddingValues(bottom = contentPadding.calculateBottomPadding() + 77.dp)) {
                    item {
                        Text(
                            text = "Опубликовано 3 дня назад",
                            style = MaterialTheme.typography.subtitle2,
                            modifier = Modifier.padding(start = 24.dp, end = 24.dp, top = 24.dp)
                        )
                        Text(
                            text = project.name,
                            style = MaterialTheme.typography.h3,
                            modifier = Modifier.padding(start = 25.dp, top = 4.dp, end = 24.dp)
                        )
                        Text(
                            text = project.description,
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
                    itemsIndexed(project.members.toList()) { index, member ->
                        MemberCard(
                            member = member,
                            onProfileClick = {},
                            user = if (index < users.size) users[index] else User()
                        )
                    }
                }
            }
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom
            ) {
                Row(modifier = Modifier.padding(bottom = 15.dp + contentPadding.calculateBottomPadding())) {
                    ExtendedFloatingActionButton(
                        text = {
                            Text(
                                text = "Завершить",
                                style = MaterialTheme.typography.button
                            )
                        },
                        onClick = { onEndProject() },
                        backgroundColor = Color(0xFF99879D),
                        modifier = Modifier
                            .height(54.dp)
                            .width(153.dp),
                        shape = RoundedCornerShape(
                            topStart = 40.dp,
                            topEnd = 0.dp,
                            bottomEnd = 0.dp,
                            bottomStart = 40.dp
                        )
                    )
                    ExtendedFloatingActionButton(
                        text = {
                            Text(
                                text = "Набрать",
                                style = MaterialTheme.typography.button
                            )
                        },
                        onClick = { onCollectPeople() },
                        backgroundColor = Color(0xFF9378FF),
                        modifier = Modifier
                            .height(54.dp)
                            .width(153.dp),
                        shape = RoundedCornerShape(
                            topStart = 0.dp,
                            topEnd = 40.dp,
                            bottomEnd = 40.dp,
                            bottomStart = 0.dp
                        )
                    )
                }
            }
        }
    }
}