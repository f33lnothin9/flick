package ru.resodostudios.flick.feature.favorites.data.repository

import kotlinx.coroutines.flow.Flow
import ru.resodostudios.flick.feature.favorites.data.data_source.MovieDao
import ru.resodostudios.flick.feature.favorites.domain.model.FavoriteMovie
import ru.resodostudios.flick.feature.favorites.domain.repository.FavoritesRepository

class FavoritesRepositoryImpl(
    private val dao: MovieDao
) : FavoritesRepository {

    override suspend fun upsertMovie(movie: FavoriteMovie) = dao.upsertMovie(movie)

    override suspend fun deleteMovie(movie: FavoriteMovie) = dao.deleteMovie(movie)

    override fun getMovies(): Flow<List<FavoriteMovie>> = dao.getMovies()
}