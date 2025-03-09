package ru.mesler.androidworks.domain.repository

import ru.mesler.androidworks.domain.model.Movie

interface IMoviesRepository {
    fun getList(): List<Movie>

    fun getById(id: Int): Movie?
}