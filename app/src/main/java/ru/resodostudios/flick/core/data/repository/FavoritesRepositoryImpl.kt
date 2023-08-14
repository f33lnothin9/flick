package ru.resodostudios.flick.core.data.repository

import kotlinx.coroutines.flow.Flow
import ru.resodostudios.flick.core.database.dao.MovieDao
import ru.resodostudios.flick.feature.favorites.domain.model.FavoriteMovie
import ru.resodostudios.flick.feature.favorites.domain.repository.FavoritesRepository
import javax.inject.Inject

class FavoritesRepositoryImpl @Inject constructor(
    private val dao: MovieDao
) : FavoritesRepository {

    override suspend fun upsertMovie(movie: FavoriteMovie) = dao.upsertMovie(movie)

    override suspend fun deleteMovie(movie: FavoriteMovie) = dao.deleteMovie(movie)

    override fun getMovies(): Flow<List<FavoriteMovie>> = dao.getMovies()
}