package ru.resodostudios.flick.core.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
import ru.resodostudios.flick.core.database.model.FavoriteMovieEntity

@Dao
interface FavoriteMoviesDao {

    @Upsert
    suspend fun upsertMovie(movie: FavoriteMovieEntity)

    @Delete
    suspend fun deleteMovie(movie: FavoriteMovieEntity)

    @Query("SELECT * FROM favorite_movies")
    fun getMovies(): Flow<List<FavoriteMovieEntity>>
}