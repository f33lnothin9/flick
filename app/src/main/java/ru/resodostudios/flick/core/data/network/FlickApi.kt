package ru.resodostudios.flick.core.data.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.resodostudios.flick.feature.movie.data.model.Movie
import ru.resodostudios.flick.feature.movies.data.model.MovieEntry
import ru.resodostudios.flick.feature.people.domain.model.Person
import ru.resodostudios.flick.feature.search.data.model.SearchedMovie
import ru.resodostudios.flick.feature.search.data.model.SearchedPeople

interface FlickApi {

    @GET("/shows")
    suspend fun getMovies(): Response<List<MovieEntry>>

    @GET("/shows/{id}")
    suspend fun getMovie(
        @Path("id") id: Int
    ): Response<Movie>

    @GET("/people")
    suspend fun getPeople(): Response<List<Person>>

    @GET("/search/shows")
    suspend fun searchMovies(
        @Query("q") query: String
    ): Response<List<SearchedMovie>>

    @GET("/search/people")
    suspend fun searchPeople(
        @Query("q") query: String
    ): Response<List<SearchedPeople>>
}