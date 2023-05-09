package com.example.studentapp.ui.navigation

import android.os.Bundle
import android.util.Log
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.studentapp.data.User
import com.example.studentapp.ui.*
import com.example.studentapp.ui.home.TAG
import com.example.studentapp.ui.profile.ProfileScreen
import com.example.studentapp.ui.profile.ProfileUiState
import com.example.studentapp.ui.profile.ProfileViewModel
import com.example.studentapp.ui.search.ActiveProjectScreen

@Composable
fun NavGraphProfile(
    profileViewModel: ProfileViewModel = viewModel(factory = ViewModelProvider.Factory),
    navState: MutableState<Bundle>,
    contentPadding: PaddingValues,
    userId: String
) {
    val profileUiState = profileViewModel.uiState.collectAsState().value
    val navController = rememberNavController()
    val context = LocalContext.current
    navController.addOnDestinationChangedListener { navControll, _, _ ->
        navState.value = navControll.saveState() ?: Bundle()
    }
    navController.restoreState(navState.value)
    NavHost(
        navController = navController,
        startDestination = ProfileScreen.route,
        modifier = Modifier.fillMaxSize()
    ) {
        composable(
            route = ActiveProjectScreen.routeWithArgs,
            arguments = listOf(navArgument(name = ActiveProjectScreen.projectId) {
                type = NavType.IntType
            })
        ) { backStackEntry ->
            ActiveProjectScreen(
                project = profileViewModel.getProjectById(
                    backStackEntry.arguments?.getInt(ActiveProjectScreen.projectId)
                        ?: error("projectId cannot be null")
                ),
                getUserById = { profileViewModel.getUserById(it) },
                contentPadding = contentPadding,
                onNavigateBack = { navController.navigateUp() },
            )
        }
        composable(
            route = DetailProjectScreen.routeWithArgs,
            arguments = listOf(navArgument(name = DetailProjectScreen.projectId) {
                type = NavType.IntType
            })
        ) { backStackEntry ->
            DetailProjectScreen(
                project = profileViewModel.getProjectById(
                    backStackEntry.arguments?.getInt(DetailProjectScreen.projectId)
                        ?: error("projectId cannot be null")
                ),
                onNavigateBack = { navController.navigateUp() },
                contentPadding = contentPadding
            )
        }
        composable(route = ProfileScreen.route) {
            ProfileScreen(
                onClickShowProjects = {
                    profileViewModel.fillProjects(0)
                    navController.navigate(DifferentProjects.route)
                },
                contentPadding = contentPadding,
                onClickCreateTeam = {
                    profileViewModel.fillProjects(0)
                    navController.navigate(ChooseProjectScreen.route)
                },
                user = profileViewModel.getUserById(0),
                textLastProject = profileViewModel.getProjectById(0).name
            )
        }
        composable(route = ChooseProjectScreen.route) {
            ChooseProjectScreen(
                onNavigateBack = { navController.navigateUp() },
                onClickProject = { navController.navigate(SearchTeammateScreen.route) },
                onCreateProject = { navController.navigate(ProjectCreationScreen.route) },
                contentPadding = contentPadding,
                leaderProjects = profileUiState.leaderProjects,
            )
        }
        composable(route = ProjectCreationScreen.route) {
            ProjectCreationScreen(
                onCreateTeam = {
                    profileViewModel.addProject(userId, context)
                    navController.navigate(SearchTeammateScreen.route)
                },
                onNavigateBack = { navController.navigateUp() },
                onNameChanged = { profileViewModel.onNameChanged(it) },
                onDescriptionChanged = { profileViewModel.onDescriptionChanged(it) },
                name = profileUiState.projectName,
                description = profileUiState.projectDescription,
                contentPadding = contentPadding
            )
        }
        composable(route = DifferentProjects.route) {
            DifferentProjectsScreen(
                onNavigateBack = { navController.navigateUp() },
                onClickActiveLeaderProject = { navController.navigate("${MyActiveProjectScreen.route}/${it}") },
                onClickActiveSubordinateProject = { navController.navigate("${ActiveProjectScreen.route}/${it}") },
                onClickNotActiveProject = { navController.navigate("${DetailProjectScreen.route}/${it}") },
                onClickCreateTeam = { navController.navigate(ProjectCreationScreen.route) },
                leaderProjects = profileUiState.leaderProjects,
                subordinateProjects = profileUiState.subordinateProjects,
                contentPadding = contentPadding
            )
        }
        composable(
            route = MyActiveProjectScreen.routeWithArgs,
            arguments = listOf(navArgument(name = MyActiveProjectScreen.projectId) {
                type = NavType.IntType
            })
        ) { backStackEntry ->
            MyActiveProjectScreen(
                project = profileViewModel.getProjectById(
                    backStackEntry.arguments?.getInt(MyActiveProjectScreen.projectId)
                        ?: error("projectId cannot be null")
                ),
                getUserById = { profileViewModel.getUserById(it) },
                onNavigateBack = { navController.navigateUp() },
                contentPadding = contentPadding,
                onCollectPeople = { navController.navigate(SearchTeammateScreen.route) },
                onEndProject = { navController.navigate(EndScreen.route) }
            )
        }
        composable(route = SearchTeammateScreen.route) {
            SearchTeammateScreen(
                onCreateTeammate = { navController.navigate(ProfileScreen.route) },
                onNavigateBack = { navController.navigateUp() }
            )
        }
        composable(route = EndScreen.route) {
            EndScreen(
                onClickEnd = { navController.navigate(ProfileScreen.route) },
                onNavigateBack = { navController.navigateUp() },
                contentPadding = contentPadding
            )
        }
    }
}