package ru.mesler.androidworks.domain.repository

import ru.mesler.androidworks.domain.model.Movie
import ru.mesler.androidworks.domain.model.MovieShort

interface IMoviesRepository {
    suspend fun getList(query: String): List<MovieShort>

    suspend fun getById(id: Int): Movie?
}