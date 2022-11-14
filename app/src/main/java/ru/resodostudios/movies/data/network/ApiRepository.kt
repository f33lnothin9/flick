package ru.resodostudios.movies.data.network

import javax.inject.Inject

class ApiRepository @Inject constructor(private val apiRepository: ApiService) {

    suspend fun getMovies() = apiRepository.getMovies()
}