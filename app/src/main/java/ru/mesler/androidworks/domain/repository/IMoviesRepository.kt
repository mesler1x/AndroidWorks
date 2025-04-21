package ru.mesler.androidworks.domain.repository

import ru.mesler.androidworks.domain.model.Movie
import ru.mesler.androidworks.domain.model.MovieShort
import ru.mesler.androidworks.domain.model.MovieType

interface IMoviesRepository {
    suspend fun getList(
        query: String,
        filterTypes: Set<MovieType>? =null
    ): List<MovieShort>

    suspend fun getById(id: Int): Movie?

    suspend fun saveFavorite(movie: MovieShort)
    suspend fun getFavorites(): List<MovieShort>
}