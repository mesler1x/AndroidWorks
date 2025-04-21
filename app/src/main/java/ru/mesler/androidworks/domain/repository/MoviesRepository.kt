package ru.mesler.androidworks.domain.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.mesler.androidworks.api.MovieApi
import ru.mesler.androidworks.api.response.MovieFullResponse
import ru.mesler.androidworks.data.MovieDatabase
import ru.mesler.androidworks.data.MovieDbEntity
import ru.mesler.androidworks.data.mapper.MovieMapper
import ru.mesler.androidworks.domain.model.Genre
import ru.mesler.androidworks.domain.model.MovieShort
import ru.mesler.androidworks.domain.model.MovieType
import ru.mesler.androidworks.domain.model.Poster
import ru.mesler.androidworks.domain.model.Premiere
import ru.mesler.androidworks.domain.model.Rating

class MoviesRepository(
    private val api: MovieApi,
    private val mapper: MovieMapper,
    private val db: MovieDatabase
) : IMoviesRepository {
    override suspend fun getList(query: String, filterTypes: Set<MovieType>?) =
        withContext(Dispatchers.IO) {
            val response = api.searchMovies(query = query).search
                .orEmpty()
                .filter { movie ->
                    filterTypes.isNullOrEmpty()
                            || filterTypes.contains(MovieType.getMovieTypeByCode(movie.type))
                }
            mapper.toDomainList(response)
        }

    override suspend fun getById(id: Int) =
        withContext(Dispatchers.IO) {
            val response = api.getMovie(id)
            mapper.toDomain(response)
        }

    override suspend fun saveFavorite(movie: MovieShort) =
        withContext(Dispatchers.IO) {
            db.movieDao().insert(
                MovieDbEntity(
                    name = movie.name,
                    type = movie.type,
                    genre = movie.genres.getOrNull(0)?.name ?: "",
                    url = movie.poster.previewUrl,
                    premiere = movie.premiere.date,
                    rating = movie.rating.kp
                )
            )
        }

    override suspend fun getFavorites() =
        withContext(Dispatchers.IO) {
            db.movieDao().getAll().map {
                MovieShort(
                    id = it.id ?: 0,
                    name = it.name ?: "",
                    poster = Poster(it.url ?: "", it.url ?: ""),
                    premiere = MovieFullResponse.PremiereDto(it.premiere ?: ""),
                    rating = Rating(it.rating ?: 1.0, 1.0, 1.0),
                    genres = listOf(Genre.valueOf(it.genre.toString())),
                    type = it.type ?: ""
                )
            }
        }
}