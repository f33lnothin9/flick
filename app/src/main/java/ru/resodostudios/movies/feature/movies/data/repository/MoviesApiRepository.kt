package ru.resodostudios.movies.feature.movies.data.repository

import ru.resodostudios.movies.feature.movies.data.network.MoviesApi
import javax.inject.Inject

class MoviesApiRepository @Inject constructor(
    private val apiRepository: MoviesApi
) {

    suspend fun getMovies() = apiRepository.getMovies()

    suspend fun getMovie(id: String) = apiRepository.getMovie(id)
}