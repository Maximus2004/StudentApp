package com.example.studentapp.data

import androidx.annotation.DrawableRes
import com.example.studentapp.R

data class Project(
    val id: String,
    val name: String,
    val description: String,
    val isActive: Boolean,
    val members: List<Pair<String, Boolean>>
)

data class User(
    val id: Int,
    val name: String,
    val surname: String,
    val email: String,
    val password: String,
    val description: String,
    val leaderProjects: List<Int>,
    val subordinateProjects: List<Int>,
    @DrawableRes
    val avatar: Int
)

data class Team(
    val id: Int,
    val name: String,
    val description: String,
    val project: Int,
    val publishDate: String,
    val leader: Int,
    val tags: List<String>
)


data class FeedbackInfo(
    val name: String,
    val text: String,
    val rate: Double,
    val role: Boolean
)

// для отзывов, они будут добавлены позже
val feedbacks = listOf(
    FeedbackInfo("Роман Новиков", "Крутой!", 4.5, true),
    FeedbackInfo("Максим Дмитриев", "Просто вах-вах!", 5.0, false),
    FeedbackInfo(
        "Валера Болога",
        "Да чё тут расписывать, в принципе не буду много писать, просто человек очень хороший и добрый, и душевный",
        5.0,
        false
    )
)

// для портфолио, оно будет добавлено позже
val photos = listOf(
    R.drawable.portfolio_example,
    R.drawable.portfolio_example2,
    R.drawable.portfolio_example3,
    R.drawable.portfolio_example4,
    R.drawable.portfolio_example,
    R.drawable.portfolio_example2
)

val projects = listOf(
    Project(
        id = "0",
        name = "Android-приложение для организации мероприятий",
        description = "Мы создаём приложение, которое поможет людям в организации самых различных мероприятий, начиная от гей-парадов и заканчивая научными конференциями. Собираем команду инициативных и обучаемый ребят, которые будут готовы активно работать над общей идеей.",
        isActive = true,
        members = listOf(Pair("0", true), Pair("1", false))
    ),
    Project(
        id = "0",
        name = "Backend-приложение для геолокации автомобилей",
        description = "Продукт, который должен будет изменить мир, позволить человечеству сделать ещё один шаг навстречу летающим машинам. Мы получим кучу наград и взорвём этот мир (разработкой имеется в виду).",
        isActive = true,
        members = listOf(Pair("1", true), Pair("0", false))
    ),
    Project(
        id = "0",
        name = "Команда для хакатона",
        description = "Собираю команду для участия в хакатоне от Силиконовой долины. Задания будет принимать лично Билл Гейтс и Стив Джобс (да, воскреснет, чтобы поучаствовать в хакатоне)",
        isActive = false,
        members = listOf(Pair("0", true), Pair("2", false))
    )
)

val users = listOf(
    User(
        id = 0,
        name = "Максим",
        surname = "Дмитриев",
        email = "maks@mail.ru",
        password = "1234",
        description = "Привет, меня зовут Максим, я из Москвы. Опыт работы в районе 4 лет. Буду рад сотрудничесту с вами, всегда вовремя выполняю работу, очень отвественный и вообще я молодец",
        leaderProjects = listOf(0, 2),
        subordinateProjects = listOf(1),
        avatar = R.drawable.my_icon
    ),
    User(
        id = 1,
        name = "Джастин",
        surname = "Вайер",
        email = "mail@mail.ru",
        password = "5678",
        description = "Я занимаюсь UI/UX на протяжении нескольких месяцев. Ищу здесь команду для усовершенствования своего скилла. Буду рад любой работе",
        leaderProjects = listOf(1),
        subordinateProjects = listOf(0),
        avatar = R.drawable.avatar
    ),
    User(
        id = 2,
        name = "Майкл",
        surname = "Джэксон",
        email = "none@mail.ru",
        password = "9101",
        description = "Я прекрасно пою, ищу команду для создания собственной студии. Мы взорвём стадионы и покорим сердца миллионов слушателей",
        leaderProjects = listOf(),
        subordinateProjects = listOf(2),
        avatar = 0
    )
)

val teams = listOf(
    Team(
        id = 0,
        name = "Backend-разработчик",
        description = "В хорошую команду ищём хорошего, инициативного, работоспособного разработчика",
        project = 1,
        publishDate = "23.04.2023",
        leader = 1,
        tags = listOf("BACKEND", "DJANGO", "REACT")
    ),
    Team(
        id = 1,
        name = "Android-разработчик",
        description = "Требуется android-разработчик, отлично знающий Jetpack Compose. Умение писать многопоточные программы также обязательно.",
        project = 0,
        publishDate = "22.04.2023",
        leader = 0,
        tags = listOf("ANDROID", "COMPOSE", "MVVM", "DEPENDENCY INJECTION")
    )
)

// для навигации
val navigationItemContentList = listOf(
    NavigationItemContent(pageType = PageType.Search, icon = R.drawable.search_icon),
    NavigationItemContent(pageType = PageType.Message, icon = R.drawable.message_icon),
    NavigationItemContent(pageType = PageType.Profile, icon = R.drawable.profile_icon)
)