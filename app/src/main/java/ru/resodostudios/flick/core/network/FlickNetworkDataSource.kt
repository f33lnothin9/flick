package ru.resodostudios.flick.core.network

import retrofit2.Response
import ru.resodostudios.flick.core.network.model.Cast
import ru.resodostudios.flick.core.network.model.Crew
import ru.resodostudios.flick.core.network.model.Movie
import ru.resodostudios.flick.core.network.model.NetworkMovie
import ru.resodostudios.flick.core.network.model.NetworkPerson
import ru.resodostudios.flick.core.network.model.NetworkSearchedMovie
import ru.resodostudios.flick.core.network.model.NetworkSearchedPeople

interface FlickNetworkDataSource {

    suspend fun getMovies(): List<NetworkMovie>

    suspend fun getMovie(id: Int): Response<Movie>

    suspend fun getPeople(): List<NetworkPerson>

    suspend fun searchMovies(query: String): Response<List<NetworkSearchedMovie>>

    suspend fun searchPeople(query: String): Response<List<NetworkSearchedPeople>>

    suspend fun getCast(id: Int): Response<List<Cast>>

    suspend fun getCrew(id: Int): Response<List<Crew>>
}