package ru.resodostudios.flick.core.data.repository.impl

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import ru.resodostudios.flick.core.common.network.Dispatcher
import ru.resodostudios.flick.core.common.network.FlickDispatchers
import ru.resodostudios.flick.core.data.repository.SearchRepository
import ru.resodostudios.flick.core.model.data.SearchMovie
import ru.resodostudios.flick.core.model.data.SearchPeople
import ru.resodostudios.flick.core.network.FlickNetworkDataSource
import ru.resodostudios.flick.core.network.model.asExternalModel
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    @Dispatcher(FlickDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    private val datasource: FlickNetworkDataSource
) : SearchRepository {

    override fun searchMovies(query: String): Flow<List<SearchMovie>> = flow {
        emit(
            datasource
                .searchMovies(query)
                .map { it.asExternalModel() }
        )
    }.flowOn(ioDispatcher)

    override fun searchPeople(query: String): Flow<List<SearchPeople>> = flow {
        emit(
            datasource
                .searchPeople(query)
                .map { it.asExternalModel() }
        )
    }.flowOn(ioDispatcher)
}