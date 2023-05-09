package com.example.studentapp.ui.navigation

import android.os.Bundle
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.studentapp.data.Team
import com.example.studentapp.data.User
import com.example.studentapp.data.users
import com.example.studentapp.ui.*
import com.example.studentapp.ui.search.*

@Composable
fun NavGraphSearch(
    navState: MutableState<Bundle>,
    contentPadding: PaddingValues,
    searchViewModel: SearchViewModel = viewModel(factory = ViewModelProvider.Factory),
) {
    val searchUiState: SearchUiState = searchViewModel.uiState.collectAsState().value
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
                onItemClick = { navController.navigate("${DetailTeammateScreen.route}/${it}") },
                contentPadding = contentPadding,
                getLeaderById = { searchViewModel.getUserById(it) },
                getProjectById = { searchViewModel.getProjectById(it) }
            )
        }
        composable(route = SearchTeammateScreen.route) {
            SearchTeammateScreen(
                onCreateTeammate = { navController.navigate(SearchScreen.route) },
                onNavigateBack = { navController.navigateUp() })
        }
        composable(
            route = DetailTeammateScreen.routeWithArgs,
            arguments = listOf(navArgument(name = DetailTeammateScreen.teamId) {
                type = NavType.IntType
            })
        ) { backStackEntry ->
            val teamId: Int = backStackEntry.arguments?.getInt(DetailTeammateScreen.teamId)
                ?: error("teamId cannot be null")
            val team: Team = searchViewModel.getTeamById(teamId)
            DetailTeammateScreen(
                team = team,
                getLeaderById = { searchViewModel.getUserById(it) },
                onClickShowProject = { navController.navigate("${ActiveProjectScreen.route}/${it}") },
                onClickReply = { navController.navigate("${ReplyScreen.route}/${ team.name }") },
                onNavigateBack = { navController.navigateUp() },
                contentPadding = contentPadding,
                membersNumber = searchViewModel.getProjectById(team.project).members.size
            )
        }
        composable(
            route = ActiveProjectScreen.routeWithArgs,
            arguments = listOf(navArgument(name = ActiveProjectScreen.projectId) {
                type = NavType.IntType
            })
        ) { backStackEntry ->
            ActiveProjectScreen(
                onNavigateBack = { navController.navigateUp() },
                contentPadding = contentPadding,
                project = searchViewModel.getProjectById(
                    backStackEntry.arguments?.getInt(ActiveProjectScreen.projectId)
                        ?: error("projectId cannot be null")
                ),
                getUserById = { searchViewModel.getUserById(it) },
                onClickProfile = { navController.navigate("${AlienScreen.route}/${it}") }
            )
        }
        composable(
            route = AlienScreen.routeWithArgs,
            arguments = listOf(navArgument(name = AlienScreen.userId) {
                type = NavType.IntType
            })
        ) { backStackEntry ->
            val userId: Int = backStackEntry.arguments?.getInt(AlienScreen.userId)
            ?: error("userId cannot be null")
            AlienProfileScreen(
                onClickShowProjects = {
                    searchViewModel.fillProjects(userId)
                    navController.navigate(DifferentProjects.route)
                },
                onNavigateBack = { navController.navigateUp() },
                user = searchViewModel.getUserById(userId),
                contentPadding = contentPadding,
                textLastProject = searchViewModel.getProjectById(0).name
            )
        }
        composable(route = ReplyScreen.routeWithArgs,
            arguments = listOf(navArgument(name = ReplyScreen.teamName) {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            ReplyScreen(
                onNavigateBack = { navController.navigateUp() },
                onClickSendButton = { navController.navigate(SearchScreen.route) },
                contentPadding = contentPadding,
                teamName = backStackEntry.arguments?.getString(ReplyScreen.teamName)
                    ?: error("userId cannot be null")
            )
        }
        composable(route = DifferentProjects.route) {
            DifferentProjectsScreen(
                onNavigateBack = { navController.navigateUp() },
                onClickActiveSubordinateProject = { navController.navigate("${ActiveProjectScreen.route}/${it}") },
                onClickNotActiveProject = { navController.navigate("${DetailProjectScreen.route}/${it}") },
                leaderProjects = searchUiState.leaderProjects,
                subordinateProjects = searchUiState.subordinateProjects,
                contentPadding = contentPadding,
                isShowingCreationButton = false
            )
        }
        composable(
            route = DetailProjectScreen.routeWithArgs,
            arguments = listOf(navArgument(name = DetailProjectScreen.projectId) {
                type = NavType.IntType
            })
        ) { backStackEntry ->
            DetailProjectScreen(
                project = searchViewModel.getProjectById(
                    backStackEntry.arguments?.getInt(DetailProjectScreen.projectId)
                        ?: error("projectId cannot be null")
                ),
                onNavigateBack = { navController.navigateUp() },
                contentPadding = contentPadding
            )
        }
    }
}