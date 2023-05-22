package com.example.studentapp.ui.navigation

import android.net.Uri
import android.nfc.Tag
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import com.example.studentapp.data.*
import com.example.studentapp.ui.*
import com.example.studentapp.ui.home.TAG
import com.example.studentapp.ui.profile.ProfileScreen
import com.example.studentapp.ui.profile.ProfileUiState
import com.example.studentapp.ui.profile.ProfileViewModel
import com.example.studentapp.ui.search.ActiveProjectScreen
import com.example.studentapp.ui.search.AlienProfileScreen
import com.example.studentapp.ui.search.AlienScreen
import kotlinx.coroutines.ExperimentalCoroutinesApi

@Composable
fun NavGraphProfile(
    profileViewModel: ProfileViewModel = viewModel(factory = ViewModelProvider.Factory),
    navState: MutableState<Bundle>,
    contentPadding: PaddingValues,
    userId: String,
    isKeyboardOpen: Boolean = false,
    user: User,
    onClickLogout: () -> Unit
) {
    val profileUiState = profileViewModel.uiState.collectAsState().value
    val projectsList = profileViewModel.projectsList.collectAsState().value
    val ratingState = profileViewModel.ratingState.collectAsState().value
    val navController = rememberNavController()
    val context = LocalContext.current
    val projectPhotos: ImageDownloadStatus = profileViewModel.projectPhotos.collectAsState().value
    navController.addOnDestinationChangedListener { navControll, _, _ ->
        navState.value = navControll.saveState() ?: Bundle()
    }
    navController.restoreState(navState.value)
    val launcherEnd = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetMultipleContents()
    ) { uris: List<Uri> ->
        profileViewModel.setProjectPhotos(uris, context)
    }
    NavHost(
        navController = navController,
        startDestination = ProfileScreen.route,
        modifier = Modifier.fillMaxSize()
    ) {
        composable(route = ActiveProjectScreen.route) {
            ActiveProjectScreen(
                project = profileUiState.currentProject,
                users = profileUiState.currentUsers,
                contentPadding = contentPadding,
                onNavigateBack = { navController.navigateUp() },
                onClickProfile = {
                    navController.navigate(AlienScreen.route)
                    profileViewModel.setCurrentUserDetail(it)
                }
            )
        }
        composable(route = DetailProjectScreen.route) {
            DetailProjectScreen(
                project = profileUiState.currentProject,
                onNavigateBack = { navController.navigateUp() },
                contentPadding = contentPadding,
                feedback = profileUiState.currentFeedback,
            )
        }
        composable(route = AlienScreen.route) {
            AlienProfileScreen(
                onClickShowProjects = {
                    profileViewModel.setProjectList(
                        projectLeaderIds = profileUiState.currentUserDetail.leaderProjects,
                        projectSubordinateIds = profileUiState.currentUserDetail.subordinateProjects
                    )
                    navController.navigate("${DifferentProjects.route}/${true}")
                },
                onNavigateBack = { navController.navigateUp() },
                user = profileUiState.currentUserDetail,
                contentPadding = contentPadding,
                textLastProject = profileUiState.currentLastProjectName,
                isAlienProfile = (profileUiState.currentUserDetail.id != UserAuthRepository.getUserId())
            )
        }
        composable(route = ProfileScreen.route) {
            ProfileScreen(
                onClickShowProjects = { navController.navigate("${DifferentProjects.route}/${false}") },
                contentPadding = contentPadding,
                onClickCreateTeam = { navController.navigate(ChooseProjectScreen.route) },
                user = user,
                textLastProject = if (projectsList.projects.first.isNotEmpty()) projectsList.projects.first.keys.last().name else "Своих проектов пока нет",
                isAlienProfile = true,
                rating = ratingState,
                numberOfProjects = if (projectsList.projects.first.isEmpty() && projectsList.projects.second.isEmpty()) 0 else projectsList.projects.first.size + projectsList.projects.second.size,
                onClickLogout = onClickLogout
            )
        }
        composable(route = ChooseProjectScreen.route) {
            ChooseProjectScreen(
                onNavigateBack = { navController.navigateUp() },
                onClickProject = { navController.navigate("${SearchTeammateScreen.route}/${it}") },
                onCreateProject = { navController.navigate(ProjectCreationScreen.route) },
                contentPadding = contentPadding,
                leaderProjects = projectsList.projects.first,
            )
        }
        composable(route = ProjectCreationScreen.route) {
            ProjectCreationScreen(
                onCreateTeam = {
                    profileViewModel.addProject(userId, context,
                        onFinish = {
                            navController.navigate("${SearchTeammateScreen.route}/${it}")
                            profileViewModel.addLeaderProject(it)
                        }
                    )
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
            route = DifferentProjects.routeWithArgs,
            arguments = listOf(navArgument(name = DifferentProjects.isAlien) {
                type = NavType.BoolType
            })
        ) { backStackEntry ->
            val isAlien: Boolean = backStackEntry.arguments?.getBoolean(DifferentProjects.isAlien)
                ?: error("projectId cannot be null")
            DifferentProjectsScreen(
                onNavigateBack = { navController.navigateUp() },
                onClickActiveLeaderProject = {
                    navController.navigate("${MyActiveProjectScreen.route}/${it.id}")
                    profileViewModel.setProjectById(it)
                },
                onClickActiveSubordinateProject = {
                    navController.navigate(ActiveProjectScreen.route)
                    profileViewModel.setProjectById(it)
                },
                onClickNotActiveProject = {
                    navController.navigate(DetailProjectScreen.route)
                    profileViewModel.setProjectById(it)
                    profileViewModel.setCurrentFeedback(it)
                },
                onClickCreateTeam = { navController.navigate(ProjectCreationScreen.route) },
                leaderProjects = if (!isAlien) projectsList.projects.first else profileUiState.currentUserLeaderProjects,
                subordinateProjects = if (!isAlien) projectsList.projects.second else profileUiState.currentUserSubordinateProjects,
                contentPadding = contentPadding,
                isShowingCreationButton = true
            )
        }
        composable(
            route = MyActiveProjectScreen.routeWithArgs,
            arguments = listOf(navArgument(name = MyActiveProjectScreen.projectId) {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            val projectId: String =
                backStackEntry.arguments?.getString(MyActiveProjectScreen.projectId)
                    ?: error("projectId cannot be null")
            MyActiveProjectScreen(
                project = profileUiState.currentProject,
                users = profileUiState.currentUsers,
                onNavigateBack = { navController.navigateUp() },
                contentPadding = contentPadding,
                onCollectPeople = { navController.navigate("${SearchTeammateScreen.route}/${projectId}") },
                onEndProject = { navController.navigate("${EndScreen.route}/${projectId}") },
                onProfileClick = {
                    navController.navigate(AlienScreen.route)
                    profileViewModel.setCurrentUserDetail(it)
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
                        onFinish = { navController.navigate(ProfileScreen.route) },
                        leaderAvatar = user.avatar,
                        leaderName = user.name
                    )
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
        composable(
            route = EndScreen.routeWithArgs,
            arguments = listOf(navArgument(name = EndScreen.projectId) {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            val projectId = backStackEntry.arguments?.getString(SearchTeammateScreen.projectId)
                ?: error("projectId cannot be null")
            EndScreen(
                onClickEnd = {
                    if (projectPhotos != ImageDownloadStatus.Loading) {
                        profileViewModel.endProject(projectId, context)
                        navController.navigate(ProfileScreen.route)
                    }
                },
                onNavigateBack = { navController.navigateUp() },
                contentPadding = contentPadding,
                isKeyboardOpen = isKeyboardOpen,
                onClickDownload = { launcherEnd.launch("image/*") },
                projectPhotos = projectPhotos,
                users = profileUiState.currentUsers,
                feedbackList = profileUiState.feedbackList,
                members = profileUiState.currentProject.members,
                ratingList = profileUiState.ratingList
            )
        }
    }
}