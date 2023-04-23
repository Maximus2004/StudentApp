package com.example.studentapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.example.studentapp.data.PageType
import com.example.studentapp.ui.home.HomeScreen
import com.example.studentapp.ui.theme.StudentAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //WindowCompat.setDecorFitsSystemWindows(window, false) // убирает system bars
        setContent {
            StudentAppTheme {
                HomeScreen()
            }
        }
    }
}