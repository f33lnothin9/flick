package ru.resodostudios.flick.core.network

import ru.resodostudios.flick.core.network.model.NetworkCastCredits
import ru.resodostudios.flick.core.network.model.NetworkCrewCredits
import ru.resodostudios.flick.core.network.model.NetworkImageExtended
import ru.resodostudios.flick.core.network.model.NetworkMovie
import ru.resodostudios.flick.core.network.model.NetworkPerson
import ru.resodostudios.flick.core.network.model.NetworkSearchMovie

interface FlickNetworkDataSource {

    suspend fun getMovies(): List<NetworkMovie>

    suspend fun getMovie(id: Int): NetworkMovie

    suspend fun getMovieImages(id: Int): List<NetworkImageExtended>

    suspend fun getPeople(): List<NetworkPerson>

    suspend fun getPerson(id: Int): NetworkPerson

    suspend fun searchMovies(query: String): List<NetworkSearchMovie>

    suspend fun getCastCredits(id: Int): List<NetworkCastCredits>

    suspend fun getCrewCredits(id: Int): List<NetworkCrewCredits>
}