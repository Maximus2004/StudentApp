package com.example.studentapp.ui.navigation

import android.os.Bundle
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.studentapp.data.*
import com.example.studentapp.ui.*
import com.example.studentapp.ui.search.*

@Composable
fun NavGraphSearch(
    navState: MutableState<Bundle>,
    contentPadding: PaddingValues,
    searchViewModel: SearchViewModel = viewModel(factory = ViewModelProvider.Factory),
) {
    val searchUiState: SearchUiState = searchViewModel.uiState.collectAsState().value
    var searchText: Pair<String, Int> by remember { mutableStateOf(Pair("", 1)) }
    val teamsState: Response = searchViewModel.teamsList.collectAsState().value
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
            LaunchedEffect(searchText) {
                searchViewModel.setSearchState(searchText)
            }
            Box(modifier = Modifier.fillMaxSize()) {
                when (teamsState) {
                    is Response.Loading -> CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    is Response.Error -> Text(text = teamsState.error, modifier = Modifier.align(Alignment.Center))
                    is Response.Success -> SearchScreen(
                        onItemClick = {
                            searchViewModel.setTeamById(it)
                            navController.navigate("${DetailTeammateScreen.route}/${it}")
                        },
                        contentPadding = contentPadding,
                        teams = teamsState.teamsList,
                        searchText = searchText.first,
                        onSearchChanged = { searchText = Pair(it, searchText.second) },
                        onChooseFilter = { searchText = Pair(searchText.first, it) }
                    )
                }
            }
        }
        composable(
            route = DetailTeammateScreen.routeWithArgs,
            arguments = listOf(navArgument(name = DetailTeammateScreen.teamId) {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            val teamId: String = backStackEntry.arguments?.getString(DetailTeammateScreen.teamId)
                ?: error("teamId cannot be null")
            DetailTeammateScreen(
                team = searchUiState.currentTeam,
                onClickShowProject = {
                    searchViewModel.setProjectById(it)
                    navController.navigate(ActiveProjectScreen.route)
                },
                onClickReply = { navController.navigate("${ReplyScreen.route}/${"team.name"}") },
                onNavigateBack = { navController.navigateUp() },
                contentPadding = contentPadding,
                members = searchUiState.currentTeam.members
            )
        }
        composable(route = ActiveProjectScreen.route) {
            ActiveProjectScreen(
                onNavigateBack = { navController.navigateUp() },
                contentPadding = contentPadding,
                project = searchUiState.currentProject,
                users = searchUiState.currentUsers,
                onClickProfile = {
                    searchViewModel.setCurrentUserDetail(it)
                    navController.navigate(AlienScreen.route)
                }
            )
        }
        composable(route = AlienScreen.route) {
            AlienProfileScreen(
                onClickShowProjects = {
                    searchViewModel.setProjectList(
                        projectLeaderIds = searchUiState.currentUserDetail.leaderProjects,
                        projectSubordinateIds = searchUiState.currentUserDetail.subordinateProjects
                    )
                },
                onNavigateBack = { navController.navigateUp() },
                user = searchUiState.currentUserDetail,
                contentPadding = contentPadding,
                textLastProject = searchUiState.currentLastProjectName,
                isAlienProfile = (searchUiState.currentUserDetail.id != UserAuthRepository.getUserId())
            )
        }
        composable(
            route = ReplyScreen.routeWithArgs,
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
                leaderProjects = hashMapOf(),
                subordinateProjects = hashMapOf(),
                contentPadding = contentPadding,
                isShowingCreationButton = false
            )
        }
//        composable(
//            route = DetailProjectScreen.routeWithArgs,
//            arguments = listOf(navArgument(name = DetailProjectScreen.projectId) {
//                type = NavType.IntType
//            })
//        ) { backStackEntry ->
//            DetailProjectScreen(
//                project = searchViewModel.getProjectById(
//                    backStackEntry.arguments?.getInt(DetailProjectScreen.projectId)
//                        ?: error("projectId cannot be null")
//                ),
//                onNavigateBack = { navController.navigateUp() },
//                contentPadding = contentPadding
//            )
//        }
    }
}