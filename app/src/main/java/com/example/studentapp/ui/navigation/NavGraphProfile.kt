package com.example.studentapp.ui.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.studentapp.data.*
import com.example.studentapp.ui.*
import com.example.studentapp.ui.profile.ChooseProjectScreen
import com.example.studentapp.ui.profile.ProfileScreen
import com.example.studentapp.ui.profile.ProfileViewModel
import com.example.studentapp.ui.profile.ProjectCreationScreen
import com.example.studentapp.ui.profile.SearchTeammateScreen

@Composable
fun NavGraphProfile(
    profileViewModel: ProfileViewModel = viewModel(factory = ViewModelProvider.Factory),
    contentPadding: PaddingValues,
    login: String,
    isKeyboardOpen: Boolean = false,
    user: UserResponse,
    onClickLogout: () -> Unit
) {
    val profileUiState = profileViewModel.uiState.collectAsState().value
    val projectsUiState = profileViewModel.projectsUiState.collectAsState().value
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = ProfileScreen.route,
        modifier = Modifier.fillMaxSize()
    ) {
        composable(route = ProfileScreen.route) {
            ProfileScreen(
                contentPadding = contentPadding,
                onClickCreateTeam = {
                    profileViewModel.getProjects(login)
                    navController.navigate(ChooseProjectScreen.route)
                },
                user = user,
                onClickLogout = onClickLogout
            )
        }
        composable(route = ChooseProjectScreen.route) {
            ChooseProjectScreen(
                onNavigateBack = { navController.navigateUp() },
                onClickProject = { navController.navigate("${SearchTeammateScreen.route}/${it}") },
                onCreateProject = { navController.navigate(ProjectCreationScreen.route) },
                contentPadding = contentPadding,
                leaderProjects = projectsUiState.projects,
            )
        }
        composable(route = ProjectCreationScreen.route) {
            ProjectCreationScreen(
                onCreateProject = {
                    profileViewModel.createProject(
                        login = login,
                        name = profileUiState.projectName,
                        description = profileUiState.projectDescription
                    )
                    navController.navigate(ProfileScreen.route)
                },
                onNavigateBack = { navController.navigateUp() },
                onNameChanged = { profileViewModel.onNameChanged(it) },
                onDescriptionChanged = { profileViewModel.onDescriptionChanged(it) },
                name = profileUiState.projectName,
                description = profileUiState.projectDescription,
                contentPadding = contentPadding,
                isKeyboardOpen = isKeyboardOpen
            )
        }
        composable(
            route = SearchTeammateScreen.routeWithArgs,
            arguments = listOf(navArgument(name = SearchTeammateScreen.projectId) {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            val projectId = backStackEntry.arguments?.getString(SearchTeammateScreen.projectId)
                ?: error("projectId cannot be null")
            SearchTeammateScreen(
                onCreateTeammate = {
                    profileViewModel.createJob(
                        projectId = projectId,
                        name = profileUiState.teamName,
                        description = profileUiState.teamDescription
                    )
                    navController.navigate(ProfileScreen.route)
                },
                onNavigateBack = { navController.navigateUp() },
                name = profileUiState.teamName,
                description = profileUiState.teamDescription,
                onNameChanged = { profileViewModel.onTeamNameChanged(it) },
                onDescriptionChanged = { profileViewModel.onTeamDescriptionChanged(it) },
                onTagsChanged = { profileViewModel.onTagsChanged(it) },
                tags = profileUiState.tags,
                contentPadding = contentPadding,
                isKeyboardOpen = isKeyboardOpen
            )
        }
    }
}