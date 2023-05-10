package com.example.studentapp.ui.search

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.studentapp.data.Project
import com.example.studentapp.data.User
import com.example.studentapp.data.projects
import com.example.studentapp.data.users
import com.example.studentapp.ui.MemberCard
import com.example.studentapp.ui.TopBar
import com.example.studentapp.ui.navigation.NavigationDestination
import com.example.studentapp.ui.theme.StudentAppTheme

object ActiveProjectScreen : NavigationDestination {
    override val route: String = "ActiveProjectScreen"
    const val projectId = "projectId"
    val routeWithArgs: String = "$route/{$projectId}"
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ActiveProjectScreen(
    contentPadding: PaddingValues = PaddingValues(),
    onNavigateBack: () -> Unit = {},
    project: Project,
    users: List<User>,
    onClickProfile: (Int) -> Unit = {}
) {
    Scaffold(topBar = { TopBar(onNavigateBack = { onNavigateBack() }) }) {
        LazyColumn(modifier = Modifier.fillMaxSize(), contentPadding = contentPadding) {
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
                MemberCard(member = member, onProfileClick = { onClickProfile(0) }, user = if (index < users.size) users[index] else User())
            }
        }
    }
}