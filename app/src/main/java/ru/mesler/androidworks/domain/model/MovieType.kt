package ru.mesler.androidworks.domain.model

enum class MovieType (val displayName: String, val code: String) {
    ANIMATED_SERIES("Анимированные сериалы", "animated-series"),
    ANIME("Аниме", "anime"),
    CARTOON("Мультфильм", "cartoon"),
    MOVIE("Фильм", "movie"),
    TV_SERIES("Телесериалы", "tv-series"),
    NONE("Тип фильма отсутствует","other");

    companion object {
        fun getMovieTypeByCode(code: String?): MovieType {
            return entries.find { it.code == code } ?: NONE
        }
    }
}
