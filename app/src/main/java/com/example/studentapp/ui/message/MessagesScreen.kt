package com.example.studentapp.ui.message

import android.net.Uri
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.studentapp.R
import com.example.studentapp.data.User
import com.example.studentapp.ui.theme.StudentAppTheme

@Composable
fun MessagesScreen(
    contentPadding: PaddingValues = PaddingValues(),
    onDialogClick: (String) -> Unit,
    messageList: List<User>
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = contentPadding,
    ) {
        item {
            Text(
                text = "Сообщения",
                style = MaterialTheme.typography.overline,
                modifier = Modifier.padding(top = 56.dp, start = 16.dp, bottom = 24.dp)
            )
        }
        item {
            if (messageList.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize()) {
                    Text(text = "Здесь пока нет сообщений",
                        modifier = Modifier.align(Alignment.Center).padding(10.dp),
                        textAlign = TextAlign.Center)
                }
            }
        }
        itemsIndexed(messageList) { index, message ->
            MessageCard(message = message, onMessageClick = { onDialogClick(it) }, index = index)
        }
    }
}

@Composable
fun MessageCard(onMessageClick: (String) -> Unit, message: User, index: Int) {
    val color: Color = if (index % 2 == 0) Color(0xFFFFFFFF) else Color(0xFFFAF9FE)
    Row(
        modifier = Modifier
            .background(color)
            .fillMaxWidth()
            .height(103.dp)
            .clickable { onMessageClick(message.id) },
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(Uri.parse(message.avatar))
                .crossfade(true)
                .build(),
            contentScale = ContentScale.Crop,
            contentDescription = null,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .size(55.dp)
                .clip(CircleShape)
        )
        Column() {
            Text(text = message.name, style = MaterialTheme.typography.h5)
            Text(text = "Посмотреть сообщения", style = MaterialTheme.typography.subtitle2)
        }
        Spacer(modifier = Modifier.weight(1f))
        Image(
            painter = painterResource(id = R.drawable.vector),
            contentDescription = null,
            modifier = Modifier.padding(end = 24.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MessagesScreenPreview() {
    StudentAppTheme {
        MessagesScreen(onDialogClick = {}, messageList = listOf())
    }
}