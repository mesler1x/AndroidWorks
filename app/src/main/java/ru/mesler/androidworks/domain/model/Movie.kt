package ru.mesler.androidworks.domain.model

class Movie(
    val id: Int,
    val name: String,
    val rating: Rating,
    val description: String,
    val votes: Votes,
    val premiere: Premiere,
    val poster: Poster,
    val genres: List<Genre>,
    val countries: List<Country>,
    val persons: List<Person>
)