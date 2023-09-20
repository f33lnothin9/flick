package ru.resodostudios.flick.core.network

import ru.resodostudios.flick.core.network.model.NetworkCast
import ru.resodostudios.flick.core.network.model.NetworkCastCredits
import ru.resodostudios.flick.core.network.model.NetworkCrew
import ru.resodostudios.flick.core.network.model.NetworkMovie
import ru.resodostudios.flick.core.network.model.NetworkPerson
import ru.resodostudios.flick.core.network.model.NetworkSearchMovie
import ru.resodostudios.flick.core.network.model.NetworkSearchPeople

interface FlickNetworkDataSource {

    suspend fun getMovies(): List<NetworkMovie>

    suspend fun getMovie(id: Int): NetworkMovie

    suspend fun getPeople(): List<NetworkPerson>

    suspend fun getPerson(id: Int): NetworkPerson

    suspend fun searchMovies(query: String): List<NetworkSearchMovie>

    suspend fun searchPeople(query: String): List<NetworkSearchPeople>

    suspend fun getCast(id: Int): List<NetworkCast>

    suspend fun getCrew(id: Int): List<NetworkCrew>

    suspend fun getCastCredits(id: Int): List<NetworkCastCredits>
}