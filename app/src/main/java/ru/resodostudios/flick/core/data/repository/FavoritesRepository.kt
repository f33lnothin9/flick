package ru.resodostudios.flick.core.data.repository

import kotlinx.coroutines.flow.Flow
import ru.resodostudios.flick.core.database.model.FavoriteMovieEntity

interface FavoritesRepository {

    suspend fun upsertMovie(movie: FavoriteMovieEntity)

    suspend fun deleteMovie(movie: FavoriteMovieEntity)

    fun getMovies(): Flow<List<FavoriteMovieEntity>>
}