package ru.resodostudios.flick.core.data.repository

import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import ru.resodostudios.flick.core.network.model.Cast
import ru.resodostudios.flick.core.network.model.Crew
import ru.resodostudios.flick.core.network.model.Movie

interface MoviesRepository {

    fun getMovies(): Flow<List<ru.resodostudios.flick.core.model.data.Movie>>

    suspend fun getMovie(id: Int): Response<Movie>

    suspend fun getCast(id: Int): Response<List<Cast>>

    suspend fun getCrew(id: Int): Response<List<Crew>>
}