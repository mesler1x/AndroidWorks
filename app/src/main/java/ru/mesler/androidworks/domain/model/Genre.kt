package ru.mesler.androidworks.domain.model

enum class Genre(val displayName: String) {
    ANIME("Аниме"),
    BIOGRAPHY("Биография"),
    ACTION("Боевик"),
    WESTERN("Вестерн"),
    WAR("Военный"),
    DETECTIVE("Детектив"),
    CHILDREN("Детский"),
    ADULT("Для взрослых"),
    DOCUMENTARY("Документальный"),
    DRAMA("Драма"),
    GAME("Игра"),
    HISTORY("История"),
    COMEDY("Комедия"),
    CONCERT("Концерт"),
    SHORT_FILM("Короткометражка"),
    CRIME("Криминал"),
    MELODRAMA("Мелодрама"),
    MUSIC("Музыка"),
    ANIMATION("Мультфильм"),
    MUSICAL("Мюзикл"),
    NEWS("Новости"),
    ADVENTURE("Приключения"),
    REALITY_TV("Реальное ТВ"),
    FAMILY("Семейный"),
    SPORT("Спорт"),
    TALK_SHOW("Ток-шоу"),
    THRILLER("Триллер"),
    HORROR("Ужасы"),
    FANTASY("Фантастика"),
    NOIR("Фильм-нуар"),
    FANTASY_GENRE("Фэнтези"),
    CEREMONY("Церемония"),
    NONE("Жанр фильма отсутствует");

    companion object {
        fun getGenreByName(name: String?): Genre {
            return entries.find { it.displayName.equals(name, ignoreCase = true) } ?: NONE
        }
    }
}