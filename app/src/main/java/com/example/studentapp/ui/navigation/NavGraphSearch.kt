package com.example.studentapp.ui.navigation

import android.os.Bundle
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.studentapp.data.User
import com.example.studentapp.data.projects
import com.example.studentapp.data.teams
import com.example.studentapp.data.users
import com.example.studentapp.ui.*

fun tempFun(userId: Int): User {
    return users[userId]
}

@Composable
fun NavGraphSearch(navState: MutableState<Bundle>, contentPadding: PaddingValues) {
    val navController = rememberNavController()
    navController.addOnDestinationChangedListener { navControll, _, _ ->
        navState.value = navControll.saveState() ?: Bundle()
    }
    navController.restoreState(navState.value)
    NavHost(
        navController = navController,
        startDestination = SearchScreen.route,
        modifier = Modifier.fillMaxSize()
    ) {
        composable(route = SearchScreen.route) {
            SearchScreen(
                onItemClick = { navController.navigate(DetailTeammateScreen.route) },
                contentPadding = contentPadding,
                onClickCreateTeam = { navController.navigate(ChooseProjectScreen.route) }
            )
        }
        composable(route = ChooseProjectScreen.route) {
            ChooseProjectScreen(
                onNavigateBack = { navController.navigateUp() },
                onClickProject = { navController.navigate(SearchTeammateScreen.route) },
                onCreateProject = { navController.navigate(ProjectCreationScreen.route) },
                contentPadding = contentPadding
            )
        }
        composable(route = ProjectCreationScreen.route) {
            ProjectCreationScreen(
                onCreateTeam = { navController.navigate(SearchTeammateScreen.route) },
                onNavigateBack = { navController.navigateUp() })
        }
        composable(route = SearchTeammateScreen.route) {
            SearchTeammateScreen(
                onCreateTeammate = { navController.navigate(SearchScreen.route) },
                onNavigateBack = { navController.navigateUp() })
        }
        composable(route = DetailTeammateScreen.route) {
            DetailTeammateScreen(
                team = teams[0],
                onClickShowProject = { navController.navigate(ActiveProjectScreen.route) },
                onClickReply = { navController.navigate(ReplyScreen.route) },
                onNavigateBack = { navController.navigateUp() },
                contentPadding = contentPadding
            )
        }
        composable(route = ActiveProjectScreen.route) {
            ActiveProjectScreen(
                onNavigateBack = { navController.navigateUp() },
                contentPadding = contentPadding,
                project = projects[0],
                getUserById = { tempFun(0) },
                onClickProfile = { navController.navigate(AlienScreen.route) }
            )
        }
        composable(route = AlienScreen.route) {
            AlienProfileScreen(
                onClickShowProjects = { /*TODO*/ },
                onNavigateBack = { navController.navigateUp() },
                contentPadding = contentPadding
            )
        }
        composable(route = ReplyScreen.route) {
            ReplyScreen(
                onNavigateBack = { navController.navigateUp() },
                onClickSendButton = { navController.navigate(SearchScreen.route) },
                contentPadding = contentPadding
            )
        }
        composable(route = DetailProjectScreen.route) {
            DetailProjectScreen(
                onNavigateBack = { navController.navigateUp() },
                contentPadding = contentPadding,
                project = projects[0]
            )
        }
//        composable(route = DifferentProjects.route) {
//            DifferentProjectsScreen(
//                onNavigateBack = { navController.navigateUp() },
//                onClickProject = { navController.navigate(DetailProjectScreen.route) }
//            )
//        }
    }
}