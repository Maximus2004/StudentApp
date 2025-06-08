package com.example.studentapp.ui.message

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.studentapp.data.ConnectResponse
import com.example.studentapp.ui.theme.Red

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ChattingScreen(
    currentConnect: ConnectResponse?,
    onNavigateBack: () -> Unit
) {
    Scaffold(
        topBar = {
            if (currentConnect != null)
                MessageTopBar(
                    onNavigateBack = { onNavigateBack() },
                    name = currentConnect.login,
                )
        },
    ) {
        if (currentConnect != null)
            FromMessage(
                text = currentConnect.text,
                modifier = Modifier.padding(top = 15.dp, end = 13.dp, start = 13.dp)
            )
    }
}

@Composable
fun MessageTopBar(
    name: String,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    TopAppBar(
        elevation = 8.dp,
        backgroundColor = Color(0xFFFFFFFF),
        modifier = Modifier.height(132.dp)
    ) {
        Column() {
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(top = 6.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onNavigateBack) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        modifier = Modifier.size(21.dp),
                        tint = Color(0xFF99879D)
                    )
                }
                Text(
                    text = "Назад",
                    style = TextStyle(
                        fontWeight = FontWeight.Medium,
                        fontSize = 18.sp,
                        color = Color(0xFF99879D)
                    )
                )
            }
            Row(
                modifier = Modifier.padding(start = 15.dp, top = 7.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = name,
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 25.sp,
                        color = Color(0xFF120E21)
                    ),
                )
            }
        }
    }
}

@Composable
fun FromMessage(text: String, modifier: Modifier = Modifier) {
    Column(horizontalAlignment = Alignment.Start, modifier = modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier
                .clip(
                    shape = RoundedCornerShape(
                        topStart = 8.dp,
                        topEnd = 8.dp,
                        bottomStart = 0.dp,
                        bottomEnd = 8.dp
                    )
                )
                .background(Color(0xFF9378FF))
                .width(181.dp)
        ) {
            Text(
                text = text,
                style = TextStyle(
                    fontWeight = FontWeight.Normal,
                    fontSize = 13.sp,
                    color = Color(0xFFFFFFFF),
                    fontFamily = Red
                ),
                modifier = Modifier.padding(12.dp),
                lineHeight = 19.sp
            )
        }
    }
}
