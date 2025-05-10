package ru.mesler.androidworks.domain.model

data class Profile (
    val fio: String = "",
    val avatarUri: String = "",
    val resumeUrl: String = "",
    val position: String = "",
    val email: String = "",
    val favoriteClassTime: String = ""
)