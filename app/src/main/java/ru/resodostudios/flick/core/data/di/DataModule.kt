package ru.resodostudios.flick.core.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.resodostudios.flick.core.data.repository.FavoritesRepository
import ru.resodostudios.flick.core.data.repository.MoviesRepository
import ru.resodostudios.flick.core.data.repository.PeopleRepository
import ru.resodostudios.flick.core.data.repository.SearchRepository
import ru.resodostudios.flick.core.data.repository.impl.FavoritesRepositoryImpl
import ru.resodostudios.flick.core.data.repository.impl.MoviesRepositoryImpl
import ru.resodostudios.flick.core.data.repository.impl.PeopleRepositoryImpl
import ru.resodostudios.flick.core.data.repository.impl.SearchRepositoryImpl
import ru.resodostudios.flick.core.data.util.ConnectivityManagerNetworkMonitor
import ru.resodostudios.flick.core.data.util.NetworkMonitor
import ru.resodostudios.flick.core.network.FlickNetworkDataSource
import ru.resodostudios.flick.core.network.retrofit.RetrofitFlickNetwork

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds
    fun bindRetrofit(
        retrofit: RetrofitFlickNetwork
    ): FlickNetworkDataSource

    @Binds
    fun bindMoviesRepository(
        moviesRepositoryImpl: MoviesRepositoryImpl
    ): MoviesRepository

    @Binds
    fun bindSearchRepository(
        searchRepositoryImpl: SearchRepositoryImpl
    ): SearchRepository

    @Binds
    fun bindPeopleRepository(
        peopleRepositoryImpl: PeopleRepositoryImpl
    ): PeopleRepository

    @Binds
    fun bindFavoritesRepository(
        favoritesRepositoryImpl: FavoritesRepositoryImpl
    ): FavoritesRepository

    @Binds
    fun bindsNetworkMonitor(
        networkMonitor: ConnectivityManagerNetworkMonitor,
    ): NetworkMonitor
}