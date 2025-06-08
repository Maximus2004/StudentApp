package com.example.studentapp.ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.studentapp.ui.*
import androidx.compose.runtime.*
import com.example.studentapp.ui.signinup.*

@Composable
fun NavGraphSignInUp(
    isKeyboardOpen: Boolean,
    onClickFinishButton: (String) -> Unit,
    signInUpViewModel: SignInUpViewModel = viewModel(factory = ViewModelProvider.Factory)
) {
    val signInUpUiState = signInUpViewModel.uiState.collectAsState().value
    val navController = rememberNavController()
    val context = LocalContext.current

    NavHost(
        navController = navController,
        startDestination = LogoScreen.route,
        modifier = Modifier.fillMaxSize()
    ) {
        composable(route = LoginScreen.route) {
            LoginScreen(
                onClickAuthButton = {
                    signInUpViewModel.onClickLoginButton(
                        context,
                        onClickFinishButton
                    )
                },
                onClickRegisterButton = { navController.navigate(SignUpScreen.route) },
                password = signInUpUiState.password,
                email = signInUpUiState.email,
                onEmailChanged = { signInUpViewModel.onEmailChanged(it) },
                onPasswordChanged = { signInUpViewModel.onPasswordChanged(it) },
                isEmailError = signInUpUiState.emailError,
                isPasswordError = signInUpUiState.passwordError
            )
        }
        composable(route = SignUpScreen.route) {
            SignUpScreen(
                isKeyboardOpen = isKeyboardOpen,
                onClickAuthButton = { navController.navigate(LoginScreen.route) },
                onClickRegisterButton = {
                    signInUpViewModel.onClickRegisterButton(
                        context,
                        onClickFinishButton
                    )
                },
                onDescriptionChanged = { signInUpViewModel.onDescriptionChanged(it) },
                onNameChanged = { signInUpViewModel.onNameChanged(it) },
                onSurnameChanged = { signInUpViewModel.onSurnameChanged(it) },
                onPasswordChanged = { signInUpViewModel.onPasswordChanged(it) },
                onEmailChanged = { signInUpViewModel.onEmailChanged(it) },
                password = signInUpUiState.password,
                email = signInUpUiState.email,
                name = signInUpUiState.name,
                surname = signInUpUiState.surname,
                description = signInUpUiState.description,
                isNameError = signInUpUiState.nameError,
                isSurnameError = signInUpUiState.surnameError,
                isDescriptionError = signInUpUiState.descriptionError,
                isEmailError = signInUpUiState.emailError,
                isPasswordError = signInUpUiState.passwordError,
            )
        }
        composable(route = LogoScreen.route) {
            LogoScreen(
                onClickRegisterButton = { navController.navigate(SignUpScreen.route) },
                onClickAuthButton = { navController.navigate(LoginScreen.route) })
        }
    }
}