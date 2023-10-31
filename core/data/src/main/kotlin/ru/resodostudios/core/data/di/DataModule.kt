package ru.resodostudios.core.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.resodostudios.core.data.repository.FavoritesRepository
import ru.resodostudios.core.data.repository.MoviesRepository
import ru.resodostudios.core.data.repository.PeopleRepository
import ru.resodostudios.core.data.repository.SearchRepository
import ru.resodostudios.core.data.repository.UserDataRepository
import ru.resodostudios.core.data.repository.impl.FavoritesRepositoryImpl
import ru.resodostudios.core.data.repository.impl.MoviesRepositoryImpl
import ru.resodostudios.core.data.repository.impl.PeopleRepositoryImpl
import ru.resodostudios.core.data.repository.impl.SearchRepositoryImpl
import ru.resodostudios.core.data.repository.impl.UserDataRepositoryImpl
import ru.resodostudios.core.data.util.ConnectivityManagerNetworkMonitor
import ru.resodostudios.core.data.util.NetworkMonitor
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
    fun bindUserDataRepository(
        userDataRepositoryImpl: UserDataRepositoryImpl
    ): UserDataRepository

    @Binds
    fun bindsNetworkMonitor(
        networkMonitor: ConnectivityManagerNetworkMonitor,
    ): NetworkMonitor
}