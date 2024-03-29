package ru.resodostudios.flick.core.database

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.resodostudios.flick.core.database.dao.FavoriteMovieDao
import ru.resodostudios.flick.core.database.dao.FavoritePeopleDao

@Module
@InstallIn(SingletonComponent::class)
object DaosModule {

    @Provides
    fun providesMoviesDao(
        database: FlickDatabase,
    ): FavoriteMovieDao = database.moviesDao()

    @Provides
    fun providesPeopleDao(
        database: FlickDatabase,
    ): FavoritePeopleDao = database.peopleDao()
}