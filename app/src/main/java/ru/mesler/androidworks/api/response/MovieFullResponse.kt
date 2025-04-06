package ru.mesler.androidworks.api.response

import androidx.annotation.Keep
import ru.mesler.androidworks.domain.model.Country
import ru.mesler.androidworks.domain.model.Genre
import ru.mesler.androidworks.domain.model.Person
import ru.mesler.androidworks.domain.model.Poster
import ru.mesler.androidworks.domain.model.Premiere
import ru.mesler.androidworks.domain.model.Rating
import ru.mesler.androidworks.domain.model.Votes

@Keep
class MovieFullResponse(
    val id: Int?,
    val name: String?,
    val rating: Rating?,
    val description: String?,
    val votes: Votes?,
    val premiere: Premiere?,
    val poster: Poster?,
    val genres: List<Genre>?,
    val countries: List<Country>?,
    val persons: List<Person>?
)