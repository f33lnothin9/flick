package ru.resodostudios.flick.core.network

import retrofit2.Response
import ru.resodostudios.flick.core.network.model.Cast
import ru.resodostudios.flick.core.network.model.Crew
import ru.resodostudios.flick.core.network.model.Movie
import ru.resodostudios.flick.core.network.model.NetworkPerson
import ru.resodostudios.flick.feature.search.data.model.SearchedMovie
import ru.resodostudios.flick.feature.search.data.model.SearchedPeople

interface FlickNetworkDataSource {

    suspend fun getMovies(): Response<List<Movie>>

    suspend fun getMovie(id: Int): Response<Movie>

    suspend fun getPeople(): List<NetworkPerson>

    suspend fun searchMovies(query: String): Response<List<SearchedMovie>>

    suspend fun searchPeople(query: String): Response<List<SearchedPeople>>

    suspend fun getCast(id: Int): Response<List<Cast>>

    suspend fun getCrew(id: Int): Response<List<Crew>>
}