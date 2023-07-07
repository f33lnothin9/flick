package ru.resodostudios.movies.feature.movies.data.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import ru.resodostudios.movies.feature.movie.data.model.Movie
import ru.resodostudios.movies.feature.movies.data.model.MovieEntry

interface MoviesApi {

    @GET("/shows")
    suspend fun getMovies(): Response<List<MovieEntry>>

    @GET("/shows/{id}")
    suspend fun getMovie(
        @Path("id") id: Int
    ): Response<Movie>
}