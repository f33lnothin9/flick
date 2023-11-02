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
import ru.resodostudios.core.data.repository.offline.OfflineFavoritesRepository
import ru.resodostudios.core.data.repository.network.NetworkMoviesRepository
import ru.resodostudios.core.data.repository.network.NetworkPeopleRepository
import ru.resodostudios.core.data.repository.network.NetworkSearchRepository
import ru.resodostudios.core.data.repository.offline.OfflineUserDataRepository
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
        moviesRepositoryImpl: NetworkMoviesRepository
    ): MoviesRepository

    @Binds
    fun bindSearchRepository(
        searchRepositoryImpl: NetworkSearchRepository
    ): SearchRepository

    @Binds
    fun bindPeopleRepository(
        peopleRepositoryImpl: NetworkPeopleRepository
    ): PeopleRepository

    @Binds
    fun bindFavoritesRepository(
        favoritesRepositoryImpl: OfflineFavoritesRepository
    ): FavoritesRepository

    @Binds
    fun bindUserDataRepository(
        userDataRepositoryImpl: OfflineUserDataRepository
    ): UserDataRepository

    @Binds
    fun bindsNetworkMonitor(
        networkMonitor: ConnectivityManagerNetworkMonitor,
    ): NetworkMonitor
}