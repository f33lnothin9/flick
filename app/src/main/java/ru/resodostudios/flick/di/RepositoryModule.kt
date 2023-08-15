package ru.resodostudios.flick.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import ru.resodostudios.flick.core.data.repository.MoviesRepository
import ru.resodostudios.flick.core.data.repository.implementation.FavoritesRepositoryImpl
import ru.resodostudios.flick.core.data.repository.implementation.MoviesRepositoryImpl
import ru.resodostudios.flick.core.data.repository.implementation.PeopleRepositoryImpl
import ru.resodostudios.flick.core.data.repository.implementation.SearchRepositoryImpl
import ru.resodostudios.flick.feature.favorites.domain.repository.FavoritesRepository
import ru.resodostudios.flick.feature.people.domain.repository.PeopleRepository
import ru.resodostudios.flick.feature.search.domain.repository.SearchRepository

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

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