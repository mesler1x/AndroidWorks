package ru.mesler.androidworks.data.mapper

import ru.mesler.androidworks.api.response.MovieFullResponse
import ru.mesler.androidworks.api.response.MoviesSearchResponse
import ru.mesler.androidworks.domain.model.Genre
import ru.mesler.androidworks.domain.model.Movie
import ru.mesler.androidworks.domain.model.MovieShort
import ru.mesler.androidworks.domain.model.MovieType
import ru.mesler.androidworks.domain.model.Poster
import ru.mesler.androidworks.domain.model.Rating
import ru.mesler.androidworks.domain.model.Votes


class MovieMapper {
    fun toDomain(response: MovieFullResponse): Movie? {
        if (response.id == null
            || response.name.isNullOrBlank()
        ) return null

        return Movie(
            id = response.id.or(0),
            name = response.name,
            type = MovieType.getMovieTypeByCode(response.type),
            rating = response.rating ?: Rating(0.0, 0.0, 0.0),
            description = response.description.orEmpty(),
            votes = response.votes ?: Votes(0, 0, 0),
            premiere = response.premiere?.date ?: "",
            poster = response.poster ?: Poster("", ""),
            genres = response.genres?.map { genre -> Genre.getGenreByName(genre.name) }
                ?: emptyList(),
            countries = response.countries?.map { country -> country.name } ?: emptyList(),
            persons = response.persons ?: emptyList()
        )
    }

    private fun toSmallDomain(response: MovieFullResponse): MovieShort {
        return MovieShort(
            id = response.id ?: 0,
            name = response.name.orEmpty(),
            poster = response.poster ?: Poster("", ""),
            premiere = MovieFullResponse.PremiereDto(response.premiere?.date ?: ""),
            rating = response.rating ?: Rating(1.0, 1.0, 1.0),
            type = response.type.orEmpty(),
            genres = response.genres?.map { genre -> Genre.getGenreByName(genre.name) }
                ?: emptyList(),
        )
    }

    fun toDomainList(response: List<MovieFullResponse>) =
        response.map { movie -> toSmallDomain(movie) }

}
