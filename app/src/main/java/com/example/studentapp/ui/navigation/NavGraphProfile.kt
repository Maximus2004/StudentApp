package com.example.studentapp.ui.navigation

import android.os.Bundle
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.studentapp.LoginScreen
import com.example.studentapp.data.PageType
import com.example.studentapp.data.teams
import com.example.studentapp.ui.*
import com.example.studentapp.ui.home.HomeScreen
import com.example.studentapp.ui.home.HomeViewModel

@Composable
fun NavGraphProfile(navState: MutableState<Bundle>, contentPadding: PaddingValues) {
    val navController = rememberNavController()
    navController.addOnDestinationChangedListener { navControll, _, _ ->
        navState.value = navControll.saveState() ?: Bundle()
    }
    navController.restoreState(navState.value)
    NavHost(
        navController = navController,
        startDestination = ProfileScreen.route,
        modifier = Modifier.fillMaxSize()
    ) {
        composable(route = ActiveProjectScreen.route) {
            ActiveProjectScreen(
                onReplyButton = { navController.navigate(ReplyScreen.route) },
                onClickProfile = { navController.navigate(AlienScreen.route) },
                onNavigateBack = { navController.navigateUp() }
            )
        }
        composable(route = DetailProjectScreen.route) {
            DetailProjectScreen(
                onNavigateBack = { navController.navigateUp() },
                contentPadding = contentPadding
            )
        }
        composable(route = ProfileScreen.route) {
            ProfileScreen(
                onClickShowProjects = { navController.navigate(DifferentProjects.route) },
                contentPadding = contentPadding
            )
        }
        composable(route = DifferentProjects.route) {
            DifferentProjects(
                onNavigateBack = { navController.navigateUp() },
                onClickProject = { navController.navigate(DetailProjectScreen.route) }
            )
        }
    }
}