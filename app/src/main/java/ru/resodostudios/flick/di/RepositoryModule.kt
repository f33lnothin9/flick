package ru.resodostudios.flick.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import ru.resodostudios.flick.core.data.repository.FavoritesRepositoryImpl
import ru.resodostudios.flick.core.data.repository.MovieRepositoryImpl
import ru.resodostudios.flick.core.data.repository.MoviesRepositoryImpl
import ru.resodostudios.flick.core.data.repository.PeopleRepositoryImpl
import ru.resodostudios.flick.core.data.repository.SearchRepositoryImpl
import ru.resodostudios.flick.feature.favorites.domain.repository.FavoritesRepository
import ru.resodostudios.flick.feature.movie.domain.repository.MovieRepository
import ru.resodostudios.flick.feature.movies.domain.repository.MoviesRepository
import ru.resodostudios.flick.feature.people.domain.repository.PeopleRepository
import ru.resodostudios.flick.feature.search.domain.repository.SearchRepository

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

    @Binds
    @ViewModelScoped
    abstract fun bindMovieRepository(
        movieRepositoryImpl: MovieRepositoryImpl
    ): MovieRepository

    @Binds
    @ViewModelScoped
    abstract fun bindMoviesRepository(
        moviesRepositoryImpl: MoviesRepositoryImpl
    ): MoviesRepository

    @Binds
    @ViewModelScoped
    abstract fun bindSearchRepository(
        searchRepositoryImpl: SearchRepositoryImpl
    ): SearchRepository

    @Binds
    @ViewModelScoped
    abstract fun bindPeopleRepository(
        peopleRepositoryImpl: PeopleRepositoryImpl
    ): PeopleRepository

    @Binds
    @ViewModelScoped
    abstract fun bindFavoritesRepository(
        favoritesRepositoryImpl: FavoritesRepositoryImpl
    ): FavoritesRepository
}