package ru.resodostudios.movies.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.resodostudios.movies.core.data.network.MoviesApi
import ru.resodostudios.movies.feature.movie.data.repository.MovieRepositoryImpl
import ru.resodostudios.movies.feature.movie.domain.repository.MovieRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MovieModule {

    @Provides
    @Singleton
    fun provideMovieRepository(apiRepository: MoviesApi): MovieRepository =
        MovieRepositoryImpl(apiRepository = apiRepository)
}