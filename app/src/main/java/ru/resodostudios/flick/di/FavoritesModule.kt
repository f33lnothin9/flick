package ru.resodostudios.flick.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.resodostudios.flick.feature.favorites.data.data_source.MovieDao
import ru.resodostudios.flick.feature.favorites.data.data_source.MovieDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FavoritesModule {

    @Provides
    @Singleton
    fun provideMovieDatabase(app: Application): MovieDao {
        return Room.databaseBuilder(
            app,
            MovieDatabase::class.java,
            "favoriteMovies"
        ).build().dao
    }
}