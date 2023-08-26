package ru.resodostudios.flick.core.data.repository

import retrofit2.Response
import ru.resodostudios.flick.core.network.model.NetworkSearchedMovie
import ru.resodostudios.flick.core.network.model.NetworkSearchedPeople

interface SearchRepository {

    suspend fun searchMovies(query: String): Response<List<NetworkSearchedMovie>>

    suspend fun searchPeople(query: String): Response<List<NetworkSearchedPeople>>
}