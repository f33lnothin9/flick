package ru.resodostudios.flick.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.resodostudios.flick.core.database.dao.FavoriteMoviesDao
import ru.resodostudios.flick.core.database.model.FavoriteMovieEntity
import ru.resodostudios.flick.core.database.util.ListConverter

@Database(
    entities = [
        FavoriteMovieEntity::class
    ],
    version = 1,
    exportSchema = true
)
@TypeConverters(ListConverter::class)
abstract class FlickDatabase : RoomDatabase() {

    abstract fun movieDao(): FavoriteMoviesDao
}