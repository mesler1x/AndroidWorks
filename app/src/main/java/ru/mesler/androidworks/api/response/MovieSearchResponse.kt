package ru.mesler.androidworks.api.response

import com.google.gson.annotations.SerializedName
import ru.mesler.androidworks.api.response.MovieFullResponse.GenreDto
import ru.mesler.androidworks.domain.model.Poster

class MoviesSearchResponse(
    @SerializedName("docs")
    val search: List<MovieFullResponse>?
)

class MovieShortResponse(
    val id: Int?,
    val name: String?,
    val year: String?,
    val type: String?,
    val genres: List<GenreDto>?,
    val poster: Poster?,
)