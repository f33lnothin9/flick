package ru.resodostudios.movies.feature.movies.data.network

import javax.inject.Inject

class MoviesApiRepository @Inject constructor(
    private val apiRepository: MoviesApi
) {

    suspend fun getMovies() = apiRepository.getMovies()
}