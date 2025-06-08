package com.example.studentapp.ui.search

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.studentapp.data.JobResponse
import com.example.studentapp.ui.TeamCard
import com.example.studentapp.ui.navigation.NavigationDestination
import com.example.studentapp.ui.theme.Red

object SearchScreen : NavigationDestination {
    override val route: String = "SearchScreen"
}

@Composable
fun SearchScreen(
    searchText: String,
    onSearchChanged: (String) -> Unit,
    onItemClick: (String) -> Unit,
    jobs: List<JobResponse>,
    contentPadding: PaddingValues = PaddingValues(),
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.background),
        contentPadding = PaddingValues(bottom = contentPadding.calculateBottomPadding() + 15.dp)
    ) {
        item {
            SearchCard(searchText, onSearchChanged)
        }
        items(jobs) { job ->
            TeamCard(
                job = job,
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .padding(horizontal = 10.dp)
                    .clickable { onItemClick(job.jobId) },
            )
        }
    }
}

@Composable
fun SearchCard(
    searchText: String,
    onSearchChanged: (String) -> Unit,
) {
    Text(
        text = "Поиск",
        style = MaterialTheme.typography.overline,
        modifier = Modifier.padding(top = 56.dp, start = 16.dp, bottom = 24.dp)
    )
    SearchField(searchText, onSearchChanged)
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchField(searchText: String, onSearchChanged: (String) -> Unit) {
    val keyboardController = LocalSoftwareKeyboardController.current
    Surface(
        elevation = 8.dp,
        modifier = Modifier
            .padding(horizontal = 10.dp)
            .padding(bottom = 20.dp)
            .fillMaxWidth()
            .height(54.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        TextField(
            singleLine = true,
            value = searchText,
            onValueChange = { onSearchChanged(it) },
            trailingIcon = {
                IconButton(onClick = { keyboardController?.hide() }) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null,
                        modifier = Modifier.size(23.dp),
                        tint = Color(0xFF99879D)
                    )
                }
            },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color(0xFFFFFFFF),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(onSearch = { keyboardController?.hide() }),
            textStyle = TextStyle(
                fontSize = 16.sp,
                fontFamily = Red,
                fontWeight = FontWeight.Normal,
                color = Color(0xFF120E21)
            ),
            placeholder = { Text(text = "Поиск по тегам") }
        )
    }
}

@Composable
fun TagItem(text: String) {
    Card(backgroundColor = Color(0xFFC5BAC8), shape = RoundedCornerShape(4.dp)) {
        Row(
            modifier = Modifier
                .padding(2.dp)
                .background(Color(0xFFFFFFFF)),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.subtitle2,
                modifier = Modifier.padding(5.dp)
            )
        }
    }
}
