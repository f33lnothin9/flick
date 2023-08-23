package ru.resodostudios.flick.core.data.repository.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.resodostudios.flick.core.data.model.asEntity
import ru.resodostudios.flick.core.data.repository.FavoritesRepository
import ru.resodostudios.flick.core.database.dao.FavoriteMoviesDao
import ru.resodostudios.flick.core.database.model.FavoriteMovieEntity
import ru.resodostudios.flick.core.database.model.asExternalModel
import ru.resodostudios.flick.core.model.data.FavoriteMovie
import javax.inject.Inject

class FavoritesRepositoryImpl @Inject constructor(
    private val dao: FavoriteMoviesDao
) : FavoritesRepository {

    override suspend fun upsertMovie(movie: FavoriteMovie) =
        dao.upsertMovie(movie.asEntity())

    override suspend fun deleteMovie(movie: FavoriteMovie) =
        dao.deleteMovie(movie.asEntity())

    override fun getMovies(): Flow<List<FavoriteMovie>> =
        dao.getMoviesEntities().map { it.map(FavoriteMovieEntity::asExternalModel) }
}