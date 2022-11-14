package ru.resodostudios.movies.data.network

import retrofit2.Response
import retrofit2.http.GET
import ru.resodostudios.movies.data.models.Movie

interface ApiService {

    @GET("/shows")
    suspend fun getMovies() : Response<List<Movie>>
}