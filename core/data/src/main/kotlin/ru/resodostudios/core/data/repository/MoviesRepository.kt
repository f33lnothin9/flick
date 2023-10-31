package ru.resodostudios.core.data.repository

import kotlinx.coroutines.flow.Flow
import ru.resodostudios.flick.core.model.data.Cast
import ru.resodostudios.flick.core.model.data.Crew
import ru.resodostudios.flick.core.model.data.ImageExtended
import ru.resodostudios.flick.core.model.data.Movie

interface MoviesRepository {

    fun getMovies(): Flow<List<Movie>>

    fun getMovie(id: Int): Flow<Movie>

    fun getMovieImages(id: Int): Flow<List<ImageExtended>>

    fun getCast(id: Int): Flow<List<Cast>>

    fun getCrew(id: Int): Flow<List<Crew>>
}