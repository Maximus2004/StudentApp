package com.example.studentapp.ui.navigation

import android.os.Bundle
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
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
    val context = LocalContext.current
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
                    is Response.Loading -> CircularProgressIndicator(
                        modifier = Modifier.align(
                            Alignment.Center
                        )
                    )
                    is Response.Error -> Text(
                        text = teamsState.error,
                        modifier = Modifier.align(Alignment.Center)
                    )
                    is Response.Success -> SearchScreen(
                        onItemClick = {
                            searchViewModel.setTeamById(it)
                            navController.navigate(DetailTeammateScreen.route)
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
        composable(route = DetailTeammateScreen.route) {
            DetailTeammateScreen(
                team = searchUiState.currentTeam,
                onClickShowProject = {
                    searchViewModel.setProjectByIdForTeam(it)
                    navController.navigate(ActiveProjectScreen.route)
                },
                onClickReply = { navController.navigate(ReplyScreen.route) },
                onNavigateBack = { navController.navigateUp() },
                contentPadding = contentPadding,
                members = searchUiState.currentTeam.members,
                currentUser = UserAuthRepository.getUserId()
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
                    navController.navigate(DifferentProjects.route)
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
        composable(route = ReplyScreen.route) {
            ReplyScreen(
                onNavigateBack = { navController.navigateUp() },
                onClickSendButton = {
                    searchViewModel.addMessage(
                        text = it,
                        send = UserAuthRepository.getUserId(),
                        receive = searchUiState.currentTeam.leaderId,
                    )
                    searchViewModel.addSubordinateProject(searchUiState.currentTeam.project)
                    searchViewModel.addMemberInProject(searchUiState.currentTeam.project)
                    searchViewModel.increaseTeamNumber(searchUiState.currentTeam.id)
                    Toast.makeText(context, "Ваше сообщение отправлено руководителю", Toast.LENGTH_SHORT).show()
                    navController.navigate(SearchScreen.route)
                },
                contentPadding = contentPadding,
                teamName = searchUiState.currentTeam.name
            )
        }
        composable(route = DifferentProjects.route) {
            DifferentProjectsScreen(
                onNavigateBack = { navController.navigateUp() },
                onClickActiveSubordinateProject = {
                    navController.navigate(ActiveProjectScreen.route)
                    searchViewModel.setProjectById(it)
                },
                onClickNotActiveProject = {
                    navController.navigate(DetailProjectScreen.route)
                    searchViewModel.setProjectById(it)
                },
                leaderProjects = searchUiState.currentUserLeaderProjects,
                subordinateProjects = searchUiState.currentUserSubordinateProjects,
                contentPadding = contentPadding,
                isShowingCreationButton = false
            )
        }

        composable(route = DetailProjectScreen.route) {
            DetailProjectScreen(
                project = searchUiState.currentProject,
                onNavigateBack = { navController.navigateUp() },
                contentPadding = contentPadding,
                feedback = Feedback()
            )
        }
    }
}