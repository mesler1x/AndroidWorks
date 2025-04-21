package ru.mesler.androidworks.domain.model

class Movie(
    val id: Int,
    val name: String,
    val type: MovieType,
    val rating: Rating,
    val description: String,
    val votes: Votes,
    val premiere: String = "",
    val poster: Poster,
    val genres: List<Genre>,
    val countries: List<String>,
    val persons: List<Person>
)