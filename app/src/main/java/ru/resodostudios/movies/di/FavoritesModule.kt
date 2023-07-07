package ru.resodostudios.movies.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.resodostudios.movies.feature.favorites.data.data_source.MovieDatabase
import ru.resodostudios.movies.feature.favorites.presentation.FavoritesViewModel
import ru.resodostudios.movies.feature.movie.domain.use_case.GetMovieUseCase
import ru.resodostudios.movies.feature.movie.presentation.MovieViewModel
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FavoritesModule {

    @Provides
    @Singleton
    fun provideMovieDatabase(app: Application): MovieDatabase {
        return Room.databaseBuilder(
            app,
            MovieDatabase::class.java,
            "favoriteMovies"
        ).build()
    }

    @Provides
    @Singleton
    fun provideFavoritesViewModel(db: MovieDatabase) = FavoritesViewModel(db.dao)
}