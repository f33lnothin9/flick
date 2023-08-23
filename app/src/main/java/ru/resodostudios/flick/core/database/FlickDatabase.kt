package ru.resodostudios.flick.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.resodostudios.flick.core.database.dao.MovieDao
import ru.resodostudios.flick.core.database.model.FavoriteMovieEntity

@Database(
    entities = [
        FavoriteMovieEntity::class
    ],
    version = 1,
    exportSchema = true
)
abstract class FlickDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao
}