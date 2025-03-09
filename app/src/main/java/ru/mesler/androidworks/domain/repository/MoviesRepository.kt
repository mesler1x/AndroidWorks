package ru.mesler.androidworks.domain.repository

import ru.mesler.androidworks.mock.MoviesData

class MoviesRepository : IMoviesRepository {
    override fun getList() = MoviesData.movies

    override fun getById(id: Int) = MoviesData.movies.find { it.id == id }
}