package com.example.studentapp.ui

import android.annotation.SuppressLint
import android.net.Uri
import android.widget.ImageButton
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.studentapp.R
import com.example.studentapp.data.*
import com.example.studentapp.ui.navigation.NavigationDestination
import com.example.studentapp.ui.theme.Red
import com.example.studentapp.ui.theme.StudentAppTheme

object EndScreen : NavigationDestination {
    override val route: String = "EndScreen"
    const val projectId = "projectId"
    val routeWithArgs: String = "${route}/{$projectId}"
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun EndScreen(
    onClickEnd: () -> Unit = {},
    onNavigateBack: () -> Unit = {},
    contentPadding: PaddingValues = PaddingValues(),
    isKeyboardOpen: Boolean = false,
    onClickDownload: () -> Unit,
    projectPhotos: ImageDownloadStatus,
    users: List<User>,
    feedbackList: MutableList<String>,
    members: HashMap<String, Boolean>,
    ratingList: MutableList<Int>
) {
    Scaffold(topBar = { TopBar(onNavigateBack = { onNavigateBack() }) }) {
        Box() {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(24.dp),
                contentPadding = PaddingValues(
                    top = 15.dp,
                    bottom = if (isKeyboardOpen) contentPadding.calculateBottomPadding() + 315.dp else contentPadding.calculateBottomPadding() + 75.dp
                )
            ) {
                itemsIndexed(users) { index, user ->
                    if (members[user.id] == false) {
                        FeedbackCard(
                            name = user.name,
                            onFeedbackChange = {
                                if (index >= feedbackList.size)
                                    feedbackList.add(it)
                                else
                                    feedbackList[index] = it
                            },
                            feedbackText = if (index < feedbackList.size) feedbackList[index] else "",
                            onRatingChange = {
                                if (index >= ratingList.size)
                                    ratingList.add(it)
                                else
                                    ratingList[index] = it
                            },
                            rating = if (index < ratingList.size) ratingList[index] else 0
                        )
                    }
                }
                item {
                    Button(
                        onClick = { onClickDownload() },
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color(0xFFF2F2F2),
                            contentColor = Color(0xFF595959)
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 13.dp, end = 13.dp, bottom = 25.dp)
                    ) {
                        Row(modifier = Modifier.padding(vertical = 10.dp)) {
                            Text(
                                text = "Загрузите фото",
                                style = MaterialTheme.typography.subtitle1
                            )
                            Spacer(modifier = Modifier.weight(1f))
                            Image(
                                painter = painterResource(id = R.drawable.upload_icon),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(17.dp)
                                    .padding(top = 4.dp)
                            )
                        }
                    }
                }
            }
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom
            ) {
                ExtendedFloatingActionButton(
                    text = {
                        if (projectPhotos == ImageDownloadStatus.Loading)
                            CircularProgressIndicator(
                                modifier = Modifier.size(30.dp),
                                color = Color.White
                            )
                        else
                            Text(
                                text = "Завершить проект",
                                style = MaterialTheme.typography.button
                            )
                    },
                    onClick = { onClickEnd() },
                    backgroundColor = Color(0xFF9378FF),
                    modifier = Modifier
                        .padding(bottom = contentPadding.calculateBottomPadding() + 15.dp)
                        .height(54.dp)
                        .width(263.dp)
                )
            }
        }
    }
}

@Composable
fun StarRating(modifier: Modifier = Modifier, rating: Int, onRatingChange: (Int) -> Unit) {
    val maxRating = 5

    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        for (rate in 1..maxRating) {
            val isSelected = rate <= rating
            val starIcon = if (isSelected) Icons.Default.Star else Icons.Default.StarBorder
            val tint = if (isSelected) MaterialTheme.colors.primary else MaterialTheme.colors.onSurface
            IconButton(onClick = { onRatingChange(rate) }, modifier = Modifier.size(20.dp)) {
                Icon(
                    imageVector = starIcon,
                    contentDescription = null,
                    tint = tint
                )
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun FeedbackCard(
    name: String,
    feedbackText: String,
    onFeedbackChange: (String) -> Unit,
    onRatingChange: (Int) -> Unit,
    rating: Int
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        Text(text = name, style = MaterialTheme.typography.h5)
        TextField(
            singleLine = true,
            value = feedbackText,
            onValueChange = { onFeedbackChange(it) },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color(0xFFF2F2F2),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() }),
            label = { Text(text = "Напишите отзыв") },
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .padding(vertical = 13.dp)
                .fillMaxWidth()
        )
        Text(
            text = "Оценка",
            style = MaterialTheme.typography.h6,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        StarRating(modifier = Modifier.width(100.dp).height(23.dp), rating = rating, onRatingChange = onRatingChange)
        Divider(
            thickness = 1.dp,
            modifier = Modifier.padding(top = 19.dp, start = 12.dp, end = 12.dp)
        )
    }
}