package ru.resodostudios.movies.core.data.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.resodostudios.movies.feature.movie.data.model.Movie
import ru.resodostudios.movies.feature.movies.data.model.MovieEntry
import ru.resodostudios.movies.feature.search.data.model.SearchedMovie

interface MoviesApi {

    @GET("/shows")
    suspend fun getMovies(): Response<List<MovieEntry>>

    @GET("/shows/{id}")
    suspend fun getMovie(
        @Path("id") id: Int
    ): Response<Movie>

    @GET("/search/shows")
    suspend fun searchMovies(
        @Query("q") query: String
    ): Response<List<SearchedMovie>>
}