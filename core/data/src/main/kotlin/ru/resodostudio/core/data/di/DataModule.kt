package ru.resodostudio.core.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.resodostudio.core.data.repository.MoviesRepository
import ru.resodostudio.core.data.repository.PeopleRepository
import ru.resodostudio.core.data.repository.SearchRepository
import ru.resodostudio.core.data.repository.UserDataRepository
import ru.resodostudio.core.data.repository.network.NetworkMoviesRepository
import ru.resodostudio.core.data.repository.network.NetworkPeopleRepository
import ru.resodostudio.core.data.repository.network.NetworkSearchRepository
import ru.resodostudio.core.data.repository.offline.OfflineUserDataRepository
import ru.resodostudio.core.data.util.ConnectivityManagerNetworkMonitor
import ru.resodostudio.core.data.util.NetworkMonitor
import ru.resodostudio.flick.core.network.FlickNetworkDataSource
import ru.resodostudio.flick.core.network.retrofit.RetrofitFlickNetwork

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

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
    fun bindUserDataRepository(
        userDataRepositoryImpl: OfflineUserDataRepository
    ): UserDataRepository

    @Binds
    fun bindsNetworkMonitor(
        networkMonitor: ConnectivityManagerNetworkMonitor,
    ): NetworkMonitor

    @Binds
    fun bindRetrofit(
        retrofit: RetrofitFlickNetwork
    ): FlickNetworkDataSource
}