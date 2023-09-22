package ru.resodostudios.flick.core.database

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.resodostudios.flick.core.database.dao.FavoriteMoviesDao
import ru.resodostudios.flick.core.database.dao.FavoritePeopleDao
import ru.resodostudios.flick.core.database.model.FavoriteMovieEntity
import ru.resodostudios.flick.core.database.model.FavoritePersonEntity
import ru.resodostudios.flick.core.database.util.ListConverter

@Database(
    entities = [
        FavoriteMovieEntity::class,
        FavoritePersonEntity::class
    ],
    version = 2,
    autoMigrations = [
        AutoMigration(from = 1, to = 2)
    ],
    exportSchema = true
)
@TypeConverters(
    ListConverter::class
)
abstract class FlickDatabase : RoomDatabase() {

    abstract fun moviesDao(): FavoriteMoviesDao

    abstract fun peopleDao(): FavoritePeopleDao
}