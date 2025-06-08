package com.example.studentapp.ui.navigation

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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.studentapp.ui.*
import com.example.studentapp.ui.search.*

@Composable
fun NavGraphSearch(
    contentPadding: PaddingValues,
    login: String,
    searchViewModel: SearchViewModel = viewModel(factory = ViewModelProvider.Factory),
) {
    val searchUiState: SearchUiState = searchViewModel.uiState.collectAsState().value
    val jobsState: Response = searchViewModel.jobsList.collectAsState().value
    val navController = rememberNavController()
    val context = LocalContext.current
    NavHost(
        navController = navController,
        startDestination = SearchScreen.route,
        modifier = Modifier.fillMaxSize()
    ) {
        composable(route = SearchScreen.route) {
            LaunchedEffect(Unit) {
                searchViewModel.updateJobList()
            }

            Box(modifier = Modifier.fillMaxSize()) {
                when (jobsState) {
                    is Response.Loading -> CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                    is Response.Error -> Text(
                        text = jobsState.error,
                        modifier = Modifier.align(Alignment.Center)
                    )
                    is Response.Success -> SearchScreen(
                        onItemClick = {
                            searchViewModel.setTeamById(it)
                            navController.navigate(DetailTeammateScreen.route)
                        },
                        contentPadding = contentPadding,
                        jobs = jobsState.teamsList,
                        onSearchChanged = searchViewModel::onSearchTextChanged,
                        searchText = searchUiState.searchText
                    )
                }
            }
        }
        composable(route = DetailTeammateScreen.route) {
            DetailTeammateScreen(
                job = searchUiState.currentJob,
                onClickReply = { navController.navigate(ReplyScreen.route) },
                onNavigateBack = { navController.navigateUp() },
                contentPadding = contentPadding,
                onClickShowProject = { projectId ->
                    searchViewModel.setProjectById(projectId)
                    navController.navigate(ActiveProjectScreen.route)
                }
            )
        }
        composable(route = ActiveProjectScreen.route) {
            ActiveProjectScreen(
                onNavigateBack = { navController.navigateUp() },
                contentPadding = contentPadding,
                project = searchUiState.currentProject,
            )
        }
        composable(route = ReplyScreen.route) {
            ReplyScreen(
                onNavigateBack = { navController.navigateUp() },
                onClickSendButton = { text ->
                    searchViewModel.createConnect(
                        jobId = searchUiState.currentJob?.jobId ?: "",
                        text = text,
                        login = login
                    )
                    Toast.makeText(context, "Ваше сообщение отправлено руководителю", Toast.LENGTH_SHORT).show()
                    navController.navigate(SearchScreen.route)
                },
                contentPadding = contentPadding,
                teamName = searchUiState.currentJob?.name ?: "Сообщение руководителю"
            )
        }
    }
}