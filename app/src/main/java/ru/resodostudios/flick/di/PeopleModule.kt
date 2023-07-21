package ru.resodostudios.flick.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.resodostudios.flick.core.data.network.FlickApi
import ru.resodostudios.flick.feature.people.data.repository.PeopleRepositoryImpl
import ru.resodostudios.flick.feature.people.domain.repository.PeopleRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PeopleModule {

    @Provides
    @Singleton
    fun providePeopleRepository(apiRepository: FlickApi): PeopleRepository =
        PeopleRepositoryImpl(apiRepository = apiRepository)
}