package ru.mesler.androidworks.state

import ru.mesler.androidworks.domain.model.MovieShort
import ru.mesler.androidworks.domain.model.MovieType

interface MoviesListState {
    val items: List<MovieShort>
    val query: String
    val isEmpty: Boolean
    var hasBadge: Boolean
    var showTypesDialog: Boolean
    val typesVariants: Set<MovieType>
    var selectedTypes: Set<MovieType>
    val isLoading: Boolean
    val error: String?
}