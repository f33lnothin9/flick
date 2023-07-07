package ru.resodostudios.movies.feature.favorites.data.data_source

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
import ru.resodostudios.movies.feature.favorites.domain.model.FavoriteMovie

@Dao
interface MovieDao {

    @Upsert
    suspend fun upsertMovie(movie: FavoriteMovie)

    @Delete
    suspend fun deleteMovie(movie: FavoriteMovie)

    @Query("SELECT * FROM favoriteMovie")
    fun getMovies(): Flow<List<FavoriteMovie>>
}