package ru.resodostudios.movies.feature.favorites.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.resodostudios.movies.feature.favorites.domain.model.FavoriteMovie

interface FavoritesRepository {

    suspend fun upsertMovie(movie: FavoriteMovie)

    suspend fun deleteMovie(movie: FavoriteMovie)

    fun getMovies(): Flow<List<FavoriteMovie>>
}