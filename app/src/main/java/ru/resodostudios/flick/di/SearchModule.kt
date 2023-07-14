package ru.resodostudios.flick.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.resodostudios.flick.core.data.network.FlickApi
import ru.resodostudios.flick.feature.search.data.repository.SearchRepositoryImpl
import ru.resodostudios.flick.feature.search.domain.repository.SearchRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SearchModule {

    @Provides
    @Singleton
    fun provideSearchRepository(apiRepository: FlickApi): SearchRepository =
        SearchRepositoryImpl(apiRepository = apiRepository)
}