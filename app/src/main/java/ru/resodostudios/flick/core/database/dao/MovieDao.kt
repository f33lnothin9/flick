package ru.resodostudios.flick.core.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
import ru.resodostudios.flick.feature.favorites.domain.model.FavoriteMovie

@Dao
interface MovieDao {

    @Upsert
    suspend fun upsertMovie(movie: FavoriteMovie)

    @Delete
    suspend fun deleteMovie(movie: FavoriteMovie)

    @Query("SELECT * FROM favoriteMovie")
    fun getMovies(): Flow<List<FavoriteMovie>>
}