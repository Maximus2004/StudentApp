package com.example.studentapp.ui.search

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.studentapp.data.photos
import com.example.studentapp.ui.theme.StudentAppTheme
import androidx.compose.material.*
import androidx.compose.ui.unit.sp
import com.example.studentapp.data.User
import com.example.studentapp.data.users
import com.example.studentapp.ui.TopBar
import com.example.studentapp.ui.navigation.NavigationDestination
import com.example.studentapp.ui.profile.InfoCard
import com.example.studentapp.ui.profile.PhotoItem
import com.example.studentapp.ui.profile.UserCard
import com.example.studentapp.ui.profile.header

object AlienScreen : NavigationDestination {
    override val route: String = "AlienScreen"
    const val userId = "userId"
    val routeWithArgs: String = "${route}/{$userId}"
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AlienProfileScreen(
    onClickShowProjects: () -> Unit,
    contentPadding: PaddingValues = PaddingValues(),
    onNavigateBack: () -> Unit,
    textLastProject: String,
    user: User
) {
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
                        name = user.name + " " + user.surname,
                        modifier = Modifier.padding(bottom = 20.dp, top = 14.dp),
                        avatar = user.avatar
                    )
                    Text(text = "Описание", style = MaterialTheme.typography.h5)
                    Text(
                        text = user.description,
                        style = MaterialTheme.typography.h4,
                        modifier = Modifier.padding(top = 12.dp),
                        lineHeight = 23.sp
                    )
                    InfoCard(
                        modifier = Modifier.padding(top = 19.dp),
                        onClickShowProjects = onClickShowProjects,
                        numberOfProjects = user.subordinateProjects!!.size + user.leaderProjects!!.size,
                        textLastProject = textLastProject
                    )
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
        AlienProfileScreen(onClickShowProjects = {}, onNavigateBack = {}, user = users[0], textLastProject = "Android-приложение для")
    }
}