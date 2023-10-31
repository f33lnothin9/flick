package ru.resodostudios.core.data.repository

import kotlinx.coroutines.flow.Flow
import ru.resodostudios.flick.core.model.data.FavoriteMovie
import ru.resodostudios.flick.core.model.data.FavoritePerson

interface FavoritesRepository {

    suspend fun upsertMovie(movie: FavoriteMovie)

    suspend fun deleteMovie(movie: FavoriteMovie)

    fun getMovies(): Flow<List<FavoriteMovie>>

    suspend fun upsertPerson(person: FavoritePerson)

    suspend fun deletePerson(person: FavoritePerson)

    fun getPeople(): Flow<List<FavoritePerson>>
}