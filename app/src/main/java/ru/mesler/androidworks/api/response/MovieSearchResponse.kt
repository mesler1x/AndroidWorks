package ru.mesler.androidworks.api.response

import com.google.gson.annotations.SerializedName
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
    val poster: Poster?,
)