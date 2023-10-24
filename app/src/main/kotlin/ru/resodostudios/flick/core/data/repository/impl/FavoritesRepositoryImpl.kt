package ru.resodostudios.flick.core.data.repository.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.resodostudios.flick.core.data.model.asEntity
import ru.resodostudios.flick.core.data.repository.FavoritesRepository
import ru.resodostudios.flick.core.database.dao.FavoriteMoviesDao
import ru.resodostudios.flick.core.database.dao.FavoritePeopleDao
import ru.resodostudios.flick.core.database.model.FavoriteMovieEntity
import ru.resodostudios.flick.core.database.model.FavoritePersonEntity
import ru.resodostudios.flick.core.database.model.asExternalModel
import ru.resodostudios.flick.core.model.data.FavoriteMovie
import ru.resodostudios.flick.core.model.data.FavoritePerson
import javax.inject.Inject

class FavoritesRepositoryImpl @Inject constructor(
    private val moviesDao: FavoriteMoviesDao,
    private val peopleDao: FavoritePeopleDao
) : FavoritesRepository {

    override suspend fun upsertMovie(movie: FavoriteMovie) =
        moviesDao.upsertMovie(movie.asEntity())

    override suspend fun deleteMovie(movie: FavoriteMovie) =
        moviesDao.deleteMovie(movie.asEntity())

    override fun getMovies(): Flow<List<FavoriteMovie>> =
        moviesDao.getMoviesEntities().map { it.map(FavoriteMovieEntity::asExternalModel) }

    override suspend fun upsertPerson(person: FavoritePerson) =
        peopleDao.upsertPerson(person.asEntity())

    override suspend fun deletePerson(person: FavoritePerson) =
        peopleDao.deletePerson(person.asEntity())

    override fun getPeople(): Flow<List<FavoritePerson>> =
        peopleDao.getPeopleEntities().map { it.map(FavoritePersonEntity::asExternalModel) }
}