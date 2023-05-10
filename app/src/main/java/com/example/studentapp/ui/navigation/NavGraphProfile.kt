package com.example.studentapp.ui.navigation

import android.nfc.Tag
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
import com.example.studentapp.data.Project
import com.example.studentapp.data.User
import com.example.studentapp.data.projects
import com.example.studentapp.data.users
import com.example.studentapp.ui.*
import com.example.studentapp.ui.home.TAG
import com.example.studentapp.ui.profile.ProfileScreen
import com.example.studentapp.ui.profile.ProfileUiState
import com.example.studentapp.ui.profile.ProfileViewModel
import com.example.studentapp.ui.search.ActiveProjectScreen
import kotlinx.coroutines.ExperimentalCoroutinesApi

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
    if (profileUiState.mainUser == User()) {
        profileViewModel.getUserById(userId)
    }
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
                project = profileUiState.currentProject,
                users = profileUiState.currentUsers,
                contentPadding = contentPadding,
                onNavigateBack = { navController.navigateUp() },
            )
        }
        composable(route = DetailProjectScreen.route) {
            DetailProjectScreen(
                project = profileUiState.currentProject,
                onNavigateBack = { navController.navigateUp() },
                contentPadding = contentPadding
            )
        }
        composable(route = ProfileScreen.route) {
            ProfileScreen(
                onClickShowProjects = {
                    profileViewModel.fillProjects()
                    navController.navigate(DifferentProjects.route)
                },
                contentPadding = contentPadding,
                onClickCreateTeam = {
                    profileViewModel.fillProjects()
                    navController.navigate(ChooseProjectScreen.route)
                },
                user = profileUiState.mainUser,
                textLastProject = "Android-приложение для блаблабла"
            )
        }
        composable(route = ChooseProjectScreen.route) {
            ChooseProjectScreen(
                onNavigateBack = { navController.navigateUp() },
                onClickProject = { navController.navigate("${SearchTeammateScreen.route}/${it}") },
                onCreateProject = { navController.navigate(ProjectCreationScreen.route) },
                contentPadding = contentPadding,
                leaderProjects = profileUiState.leaderProjects,
            )
        }
        composable(route = ProjectCreationScreen.route) {
            ProjectCreationScreen(
                onCreateTeam = {
                    profileViewModel.addProject(userId, context,
                        onFinish = {
                            profileViewModel.addLeaderProject(it)
                            navController.navigate("${SearchTeammateScreen.route}/${it}")
                        }
                    )
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
                onClickActiveLeaderProject = {
                    profileViewModel.setProjectById(it)
                    navController.navigate("${MyActiveProjectScreen.route}/${it}")
                },
                onClickActiveSubordinateProject = {
                    profileViewModel.setProjectById(it)
                    navController.navigate("${ActiveProjectScreen.route}/${it}")
                },
                onClickNotActiveProject = {
                    profileViewModel.setProjectById(it)
                    navController.navigate(DetailProjectScreen.route)
                },
                onClickCreateTeam = { navController.navigate(ProjectCreationScreen.route) },
                leaderProjects = profileUiState.leaderProjects,
                subordinateProjects = profileUiState.subordinateProjects,
                contentPadding = contentPadding
            )
        }
        composable(
            route = MyActiveProjectScreen.routeWithArgs,
            arguments = listOf(navArgument(name = MyActiveProjectScreen.projectId) {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            val projectId: String = backStackEntry.arguments?.getString(MyActiveProjectScreen.projectId)
                ?: error("projectId cannot be null")
            MyActiveProjectScreen(
                project = profileUiState.currentProject,
                users = profileUiState.currentUsers,
                onNavigateBack = {
                    profileViewModel.resetUsersAndProject()
                    navController.navigateUp()
                },
                contentPadding = contentPadding,
                onCollectPeople = {
                    profileViewModel.resetUsersAndProject()
                    navController.navigate("${SearchTeammateScreen.route}/${projectId}")
                },
                onEndProject = {
                    navController.navigate("${EndScreen.route}/${projectId}")
                }
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
                    profileViewModel.addTeam(
                        context = context,
                        userId = userId,
                        projectId = projectId,
                        onFinish = { navController.navigate(ProfileScreen.route) }
                    )
                },
                onNavigateBack = { navController.navigateUp() },
                name = profileUiState.teamName,
                description = profileUiState.teamDescription,
                onNameChanged = { profileViewModel.onTeamNameChanged(it) },
                onDescriptionChanged = { profileViewModel.onTeamDescriptionChanged(it) },
                onTagsChanged = { profileViewModel.onTagsChanged(it) },
                tags = profileUiState.tags,
                contentPadding = contentPadding
            )
        }
        composable(route = EndScreen.routeWithArgs,
            arguments = listOf(navArgument(name = EndScreen.projectId) {
                type = NavType.StringType
            })){ backStackEntry ->
            val projectId = backStackEntry.arguments?.getString(SearchTeammateScreen.projectId)
                ?: error("projectId cannot be null")
            EndScreen(
                onClickEnd = {
                    profileViewModel.endProject(projectId)
                    navController.navigate(ProfileScreen.route)
                },
                onNavigateBack = { navController.navigateUp() },
                contentPadding = contentPadding
            )
        }
    }
}