package ru.mesler.androidworks.mapper

import ru.mesler.androidworks.api.response.MovieFullResponse
import ru.mesler.androidworks.api.response.MoviesSearchResponse
import ru.mesler.androidworks.domain.model.Movie
import ru.mesler.androidworks.domain.model.MovieShort
import ru.mesler.androidworks.domain.model.Poster
import ru.mesler.androidworks.domain.model.Premiere
import ru.mesler.androidworks.domain.model.Rating
import ru.mesler.androidworks.domain.model.Votes


class MovieMapper {
    fun toDomain(response: MovieFullResponse): Movie? {
        if (response.id == null || response.name == null) return null

        return Movie(
            id = response.id.or(0),
            name = response.name,
            rating = response.rating ?: Rating(0.0, 0.0, 0.0),
            description = response.description.orEmpty(),
            votes = response.votes ?: Votes(0, 0, 0),
            premiere = response.premiere ?: Premiere(""),
            poster = response.poster ?: Poster("", ""),
            genres = response.genres ?: emptyList(),
            countries = response.countries ?: emptyList(),
            persons = response.persons ?: emptyList()
        )
    }

    private fun toSmallDomain(response: MovieFullResponse): MovieShort {
        return MovieShort(
            id = response.id ?: 0,
            name = response.name.orEmpty(),
            poster = response.poster ?: Poster("", ""),
            premiere = response.premiere ?: Premiere(""),
            rating = response.rating ?: Rating(1.0, 1.0, 1.0)
        )
    }

    fun toDomainList(response: MoviesSearchResponse) =
        response.search?.map { movie -> toSmallDomain(movie) }.orEmpty()

}
