package com.example.studentapp.ui.signinup

import android.net.Uri
import kotlinx.coroutines.Deferred

sealed interface Response {
    data class Success(val avatarUri: Uri): Response
    object Error: Response
    object Loading: Response
    object Default: Response
}