package ru.mesler.androidworks.api

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.mesler.androidworks.api.response.MovieFullResponse
import ru.mesler.androidworks.api.response.MoviesSearchResponse

interface MovieApi {
    @GET("movie/search")
    suspend fun searchMovies(
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 10,
        @Query("query") query: String,
    ): MoviesSearchResponse

    @GET("movie/{id}")
    suspend fun getMovie(
        @Path("id") id: Int? = null,
    ): MovieFullResponse
}