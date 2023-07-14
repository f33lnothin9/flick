package ru.resodostudios.flick.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.resodostudios.flick.core.data.network.MoviesApi
import ru.resodostudios.flick.feature.movie.data.repository.MovieRepositoryImpl
import ru.resodostudios.flick.feature.movie.domain.repository.MovieRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MovieModule {

    @Provides
    @Singleton
    fun provideMovieRepository(apiRepository: MoviesApi): MovieRepository =
        MovieRepositoryImpl(apiRepository = apiRepository)
}