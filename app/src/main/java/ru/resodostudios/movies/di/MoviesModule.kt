package ru.resodostudios.movies.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.resodostudios.movies.core.data.network.MoviesApi
import ru.resodostudios.movies.feature.movies.data.repository.MoviesRepositoryImpl
import ru.resodostudios.movies.feature.movies.domain.repository.MoviesRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MoviesModule {

    @Provides
    @Singleton
    fun provideMoviesRepository(apiRepository: MoviesApi): MoviesRepository =
        MoviesRepositoryImpl(apiRepository = apiRepository)
}