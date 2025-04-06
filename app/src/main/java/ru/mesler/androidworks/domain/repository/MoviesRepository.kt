package ru.mesler.androidworks.domain.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.mesler.androidworks.api.MovieApi
import ru.mesler.androidworks.mapper.MovieMapper

class MoviesRepository(
    private val api: MovieApi,
    private val mapper: MovieMapper
) : IMoviesRepository {
    override suspend fun getList(query: String) =
        withContext(Dispatchers.IO) {
            val response = api.searchMovies(query = query)
            mapper.toDomainList(response)
        }

    override suspend fun getById(id: Int) =
        withContext(Dispatchers.IO) {
            val response = api.getMovie(id)
            mapper.toDomain(response)
        }
}