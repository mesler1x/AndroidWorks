package ru.mesler.androidworks.api.response

import androidx.annotation.Keep
import ru.mesler.androidworks.domain.model.Person
import ru.mesler.androidworks.domain.model.Poster
import ru.mesler.androidworks.domain.model.Rating
import ru.mesler.androidworks.domain.model.Votes

@Keep
class MovieFullResponse(
    val id: Int?,
    val name: String?,
    val rating: Rating?,
    val description: String?,
    val votes: Votes?,
    val type: String,
    val premiere: PremiereDto?,
    val poster: Poster?,
    val genres: List<GenreDto>?,
    val countries: List<CountryDto>?,
    val persons: List<Person>?
) {


    class PremiereDto(
        val date: String
    )

    class GenreDto(
        val name: String
    )

    class CountryDto(
        val name: String
    )
}