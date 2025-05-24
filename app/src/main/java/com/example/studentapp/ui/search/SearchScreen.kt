package com.example.studentapp.ui.search

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.studentapp.R
import com.example.studentapp.ui.theme.StudentAppTheme
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.studentapp.data.*
import com.example.studentapp.ui.TeamCard
import com.example.studentapp.ui.navigation.NavigationDestination
import com.example.studentapp.ui.theme.Red
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

object SearchScreen : NavigationDestination {
    override val route: String = "SearchScreen"
}

@Composable
fun SearchScreen(
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
            SearchCard()
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
fun SearchCard() {
    Text(
        text = "Поиск",
        style = MaterialTheme.typography.overline,
        modifier = Modifier.padding(top = 56.dp, start = 16.dp, bottom = 24.dp)
    )
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
