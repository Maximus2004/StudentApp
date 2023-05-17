package com.example.studentapp.ui

import android.util.Log
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.ime
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.studentapp.data.UserAuthRepository
import com.example.studentapp.ui.home.HomeScreen
import com.example.studentapp.ui.home.TAG
import com.example.studentapp.ui.navigation.NavGraphSignInUp
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun keyboardAsState(): State<Boolean> {
    val isImeVisible = WindowInsets.ime.getBottom(LocalDensity.current) > 0
    return rememberUpdatedState(isImeVisible)
}

@Composable
fun StudentApp(studentViewModel: StudentViewModel = viewModel(factory = ViewModelProvider.Factory)) {
    val isUserRegistered = studentViewModel.uiState.collectAsState().value.isUserRegistered
    val userUiState = studentViewModel.userState.collectAsState().value
    val systemUiController = rememberSystemUiController()
    //val isKeyboardOpen by keyboardAsState()
    if (isUserRegistered.isNotEmpty()) {
        SideEffect {
            systemUiController.setSystemBarsColor(color = Color(0xFFECECEC))
        }
        HomeScreen(userId = UserAuthRepository.getUserId(), isKeyboardOpen = false, user = userUiState)
    } else {
        SideEffect {
            systemUiController.setSystemBarsColor(color = Color.Transparent)
        }
        NavGraphSignInUp(isKeyboardOpen = true, onClickFinishButton = { studentViewModel.changeUser(UserAuthRepository.getUserId()) })
    }
}