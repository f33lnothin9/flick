package ru.resodostudios.flick.core.data.repository

import retrofit2.Response
import ru.resodostudios.flick.core.network.model.Cast
import ru.resodostudios.flick.core.network.model.Crew
import ru.resodostudios.flick.core.network.model.Movie

interface MoviesRepository {

    suspend fun getMovies(): Response<List<Movie>>

    suspend fun getMovie(id: Int): Response<Movie>

    suspend fun getCast(id: Int): Response<List<Cast>>

    suspend fun getCrew(id: Int): Response<List<Crew>>
}