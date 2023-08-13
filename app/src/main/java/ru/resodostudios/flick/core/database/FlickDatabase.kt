package ru.resodostudios.flick.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.resodostudios.flick.core.database.dao.MovieDao
import ru.resodostudios.flick.feature.favorites.domain.model.FavoriteMovie

@Database(
    entities = [FavoriteMovie::class],
    version = 1,
    exportSchema = false
)
abstract class FlickDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao
}