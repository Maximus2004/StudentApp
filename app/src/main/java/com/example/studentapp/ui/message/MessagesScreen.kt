package com.example.studentapp.ui.message

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.studentapp.R
import com.example.studentapp.ui.navigation.NavigationDestination
import com.example.studentapp.ui.theme.StudentAppTheme

object MessagesScreen : NavigationDestination {
    override val route: String = "MessagesScreen"
}

@Composable
fun MessagesScreen(contentPadding: PaddingValues = PaddingValues(), onDialogClick: () -> Unit) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = contentPadding
    ) {
        item {
            Text(
                text = "Сообщения",
                style = MaterialTheme.typography.overline,
                modifier = Modifier.padding(top = 56.dp, start = 16.dp, bottom = 24.dp)
            )
        }
        itemsIndexed(
            listOf(
                MessageItem(name = "Максим Дмитриев", text = "Здравствуйте! Очень понравилась...", avatar = R.drawable.avatar),
                MessageItem(name = "Алексей Халецкий", text = "Большое спасибо за работу!!", avatar = R.drawable.avatar)
            )
        ) { index, message ->
            MessageCard(message = message, onMessageClick = { onDialogClick() }, index = index)
        }
    }
}

@Composable
fun MessageCard(onMessageClick: () -> Unit, message: MessageItem, index: Int) {
    val color: Color = if (index%2==0) Color(0xFFFFFFFF) else Color(0xFFFAF9FE)
    Row(
        modifier = Modifier
            .background(color)
            .fillMaxWidth()
            .height(103.dp)
            .clickable { onMessageClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = message.avatar),
            contentDescription = null,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .size(55.dp)
        )
        Column() {
            Text(text = message.name, style = MaterialTheme.typography.h5)
            Text(text = message.text, style = MaterialTheme.typography.subtitle2)
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
        MessagesScreen(onDialogClick = {})
    }
}

data class MessageItem(
    val name: String,
    val text: String,
    @DrawableRes
    val avatar: Int
)