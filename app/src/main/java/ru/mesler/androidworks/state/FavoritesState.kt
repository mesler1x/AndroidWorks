package ru.mesler.androidworks.state

import ru.mesler.androidworks.domain.model.MovieShort

data class FavoritesState (
    val items: List<MovieShort> = emptyList()
)