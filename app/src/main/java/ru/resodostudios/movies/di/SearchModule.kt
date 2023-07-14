package ru.resodostudios.movies.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.resodostudios.movies.core.data.network.MoviesApi
import ru.resodostudios.movies.feature.search.data.repository.SearchRepositoryImpl
import ru.resodostudios.movies.feature.search.domain.repository.SearchRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SearchModule {

    @Provides
    @Singleton
    fun provideSearchRepository(apiRepository: MoviesApi): SearchRepository =
        SearchRepositoryImpl(apiRepository = apiRepository)
}