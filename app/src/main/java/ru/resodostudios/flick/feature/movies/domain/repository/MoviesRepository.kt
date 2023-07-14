package ru.resodostudios.flick.feature.movies.domain.repository

import retrofit2.Response
import ru.resodostudios.flick.feature.movies.data.model.MovieEntry

interface MoviesRepository {

    suspend fun getMovies(): Response<List<MovieEntry>>
}