package ru.mesler.androidworks.state

import ru.mesler.androidworks.domain.model.MovieShort

interface MoviesListState {
    val items: List<MovieShort>
    val query: String
    val isEmpty: Boolean
    val isLoading: Boolean
    val error: String?
}