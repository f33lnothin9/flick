package ru.resodostudios.flick.core.data.repository

import kotlinx.coroutines.flow.Flow
import ru.resodostudios.flick.feature.favorites.domain.model.FavoriteMovie

interface FavoritesRepository {

    suspend fun upsertMovie(movie: FavoriteMovie)

    suspend fun deleteMovie(movie: FavoriteMovie)

    fun getMovies(): Flow<List<FavoriteMovie>>
}