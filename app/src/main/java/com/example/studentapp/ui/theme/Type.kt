package com.example.studentapp.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.studentapp.R

val Red = FontFamily(
    Font(R.font.red_medium, FontWeight.Medium),
    Font(R.font.red_regular, FontWeight.Normal),
    Font(R.font.red_semibold, FontWeight.SemiBold),
    Font(R.font.red_light, FontWeight.Light),
    Font(R.font.red_bold, FontWeight.Bold),
    Font(R.font.red_black, FontWeight.Black)
)

val Typography = Typography(
    h1 = TextStyle( // h1
        fontFamily = Red,
        fontWeight = FontWeight.Medium,
        fontSize = 96.sp,
        color = Color(0xFF120E21)
    ),
    button = TextStyle( // button
        fontWeight = FontWeight.Medium,
        fontSize = 18.sp,
        color = Color(0xFFFFFFFF)
    ),
    body1 = TextStyle( // body1
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp,
        color = Color(0xFF120E21)
    ),
    body2 = TextStyle( // body2
        fontWeight = FontWeight.SemiBold,
        fontSize = 18.sp,
        color = Color(0xFF120E21)
    ),
    h2 = TextStyle( // h2
        fontWeight = FontWeight.Medium,
        fontSize = 27.sp,
        color = Color(0xFF120E21)
    ),
    subtitle1 = TextStyle( // subtitle1
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        color = Color(0xFF848484)
    ),
    subtitle2 = TextStyle( // subtitle2
        fontWeight = FontWeight.Light,
        fontSize = 13.sp,
        color = Color(0xFF99879D)
    ),
    h4 = TextStyle( // h4
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        color = Color(0xFF99879D)
    ),
    h3 = TextStyle( // h3
        fontWeight = FontWeight.Bold,
        fontSize = 25.sp,
        color = Color(0xFF120E21)
    ),
    h5 = TextStyle( // h5
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        color = Color(0xFF120E21)
    ),
    h6 = TextStyle( // h6
        fontWeight = FontWeight.Normal,
        fontSize = 13.sp,
        color = Color(0xFF120E21)
    ),
    overline = TextStyle( // overline
        fontWeight = FontWeight.Black,
        fontSize = 40.sp,
        color = Color(0xFF120E21)
    )
)