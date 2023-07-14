package ru.resodostudios.movies.feature.movie.domain.repository

import retrofit2.Response
import ru.resodostudios.movies.feature.movie.data.model.Movie

interface MovieRepository {

    suspend fun getMovie(id: Int): Response<Movie>
}