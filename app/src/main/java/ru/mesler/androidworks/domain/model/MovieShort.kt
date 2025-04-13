package ru.mesler.androidworks.domain.model

import ru.mesler.androidworks.api.response.MovieFullResponse

class MovieShort(
    val id: Int,
    val name: String,
    val poster: Poster,
    val premiere: MovieFullResponse.PremiereDto,
    val rating: Rating,
    val type: String,
    val genres: List<Genre>
)