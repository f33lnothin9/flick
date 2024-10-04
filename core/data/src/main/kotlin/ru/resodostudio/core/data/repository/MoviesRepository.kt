package ru.resodostudio.core.data.repository

import kotlinx.coroutines.flow.Flow
import ru.resodostudio.flick.core.model.data.ImageExtended
import ru.resodostudio.flick.core.model.data.Movie

interface MoviesRepository {

    fun getMovies(): Flow<List<Movie>>

    fun getMovie(id: Int): Flow<Movie>

    fun getMovieImages(id: Int): Flow<List<ImageExtended>>
}