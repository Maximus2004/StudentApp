package com.example.studentapp.ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.studentapp.ui.*
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.*
import com.example.studentapp.data.ImageDownloadStatus
import com.example.studentapp.ui.signinup.*

@Composable
fun NavGraphSignInUp(
    isKeyboardOpen: Boolean,
    onClickFinishButton: () -> Unit,
    signInUpViewModel: SignInUpViewModel = viewModel(factory = ViewModelProvider.Factory)
) {
    val signInUpUiState = signInUpViewModel.uiState.collectAsState().value
    val navController = rememberNavController()
    val context = LocalContext.current
    val avatar = signInUpViewModel.avatarState.collectAsState().value
    val portfolio = signInUpViewModel.portfolioState.collectAsState().value
    val launcherPortfolio = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetMultipleContents()
    ) { uris: List<Uri> ->
        signInUpViewModel.setProfilePhotos(uris, context)
    }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
    ) { uri: Uri? ->
        if (uri != null) {
            signInUpViewModel.uploadImageToFirebaseStorage(uri)
        }
    }

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
                    if (avatar != Response.Loading && portfolio != ImageDownloadStatus.Loading)
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
                onClickUploadAvatar = { if (avatar != Response.Loading) launcher.launch("image/*") },
                avatar = avatar,
                portfolio = portfolio,
                onClickUploadPortfolio = { if (portfolio != ImageDownloadStatus.Loading) launcherPortfolio.launch("image/*") },
            )
        }
        composable(route = LogoScreen.route) {
            LogoScreen(
                onClickRegisterButton = { navController.navigate(SignUpScreen.route) },
                onClickAuthButton = { navController.navigate(LoginScreen.route) })
        }
    }
}