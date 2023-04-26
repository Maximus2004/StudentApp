package com.example.studentapp.ui

import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.studentapp.ui.home.HomeScreen
import com.example.studentapp.ui.navigation.NavGraphSignInUp
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun StudentApp(studentViewModel: StudentViewModel = viewModel(factory = ViewModelProvider.Factory)) {
    // потом надо будет поставить сюда Data Preferences
    val isUserRegistered = studentViewModel.uiState.collectAsState().value.isUserRegistered
    val systemUiController = rememberSystemUiController()

    if (isUserRegistered != -1) {
        SideEffect {
            systemUiController.setSystemBarsColor(color = Color(0xFFECECEC))
        }
        HomeScreen(isUserRegistered)
    } else {
        SideEffect {
            systemUiController.setSystemBarsColor(color = Color.Transparent)
        }
        NavGraphSignInUp(onClickFinishButton = { studentViewModel.changeUser(it) })
    }
}