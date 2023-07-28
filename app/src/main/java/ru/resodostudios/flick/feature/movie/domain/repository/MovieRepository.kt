package ru.resodostudios.flick.feature.movie.domain.repository

import retrofit2.Response
import ru.resodostudios.flick.feature.movie.data.model.Cast
import ru.resodostudios.flick.feature.movie.data.model.Movie

interface MovieRepository {

    suspend fun getMovie(id: Int): Response<Movie>

    suspend fun getCast(id: Int): Response<List<Cast>>
}