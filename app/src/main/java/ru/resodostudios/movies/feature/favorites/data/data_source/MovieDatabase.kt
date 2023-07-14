package ru.resodostudios.movies.feature.favorites.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.resodostudios.movies.feature.favorites.domain.model.FavoriteMovie

@Database(
    entities = [FavoriteMovie::class],
    version = 1,
    exportSchema = false
)
abstract class MovieDatabase : RoomDatabase() {

    abstract val dao: MovieDao
}