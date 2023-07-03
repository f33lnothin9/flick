package ru.resodostudios.movies.feature.movies.data.network

import retrofit2.Response
import retrofit2.http.GET
import ru.resodostudios.movies.feature.movies.data.model.Movie

interface MoviesApi {

    @GET("/shows")
    suspend fun getMovies() : Response<List<Movie>>
}