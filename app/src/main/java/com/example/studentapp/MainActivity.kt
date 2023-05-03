package com.example.studentapp

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.compose.rememberNavController
import com.example.studentapp.data.PageType
import com.example.studentapp.ui.home.HomeScreen
import com.example.studentapp.ui.navigation.NavGraphSignInUp
import com.example.studentapp.ui.theme.StudentAppTheme
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import com.example.studentapp.ui.StudentApp
import com.example.studentapp.ui.home.HomeScreen
import com.example.studentapp.ui.navigation.NavGraphSignInUp
import com.google.accompanist.systemuicontroller.rememberSystemUiController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //WindowCompat.setDecorFitsSystemWindows(window, false) // убирает system bars
        //window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE); // чтобы можно было под клавой ui видеть
        setContent {
            StudentAppTheme {
                StudentApp()
            }
        }
    }
}