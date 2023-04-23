package com.example.studentapp.data

import com.example.studentapp.R
import com.example.studentapp.ui.FeedbackInfo
import com.example.studentapp.ui.Team

val feedbacks = listOf(
    FeedbackInfo("Роман Новиков", "Крутой!", 4.5, true),
    FeedbackInfo("Максим Дмитриев", "Просто вах-вах!", 5.0, false),
    FeedbackInfo("Валера Болога", "Да чё тут расписывать, в принципе не буду много писать, просто человек очень хороший и добрый, и душевный", 5.0, false)
)

val photos = listOf(
    R.drawable.portfolio_example,
    R.drawable.portfolio_example2,
    R.drawable.portfolio_example3,
    R.drawable.portfolio_example4,
    R.drawable.portfolio_example,
    R.drawable.portfolio_example2
)

val teams = listOf(
    Team(name = "Backend-разработчик", description = "В хорошую команду ищём хорошего, инициативного, работоспособного разработчика"),
    Team(name = "Android-разработчик", description = "Требуется android-разработчик, отлично знающий Jetpack Compose. Умение писать многопоточные программы также обязательно.")
)

val navigationItemContentList = listOf(
    NavigationItemContent(pageType = PageType.Search, icon = R.drawable.search_icon),
    NavigationItemContent(pageType = PageType.Message, icon = R.drawable.message_icon),
    NavigationItemContent(pageType = PageType.Profile, icon = R.drawable.profile_icon)
)