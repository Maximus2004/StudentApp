package com.example.studentapp.ui.navigation

import android.os.Bundle
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.studentapp.ui.*
import com.example.studentapp.ui.profile.LoginScreen
import com.example.studentapp.ui.profile.LogoScreen
import com.example.studentapp.ui.profile.ProfileScreen
import com.example.studentapp.ui.profile.SignUpScreen

@Composable
fun NavGraphSignInUp(onClickFinishButton: (Int) -> Unit) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = LogoScreen.route,
        modifier = Modifier.fillMaxSize()
    ) {
        composable(route = LoginScreen.route) {
            LoginScreen(
                onClickAuthButton = { onClickFinishButton(it) },
                onClickRegisterButton = { navController.navigate(SignUpScreen.route) })
        }
        composable(route = SignUpScreen.route) {
            SignUpScreen(
                onClickAuthButton = { navController.navigate(LoginScreen.route) },
                onClickRegisterButton = { onClickFinishButton(it) })
        }
        composable(route = LogoScreen.route) {
            LogoScreen(
                onClickRegisterButton = { navController.navigate(SignUpScreen.route) },
                onClickAuthButton = { navController.navigate(LoginScreen.route) })
        }
    }
}