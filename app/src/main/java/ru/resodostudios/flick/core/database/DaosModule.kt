package ru.resodostudios.flick.core.database

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.resodostudios.flick.core.database.dao.MovieDao

@Module
@InstallIn(SingletonComponent::class)
object DaosModule {

    @Provides
    fun providesTransactionsDao(
        database: FlickDatabase,
    ): MovieDao = database.movieDao()
}