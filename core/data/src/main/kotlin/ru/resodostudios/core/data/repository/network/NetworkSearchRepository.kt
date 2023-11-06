package ru.resodostudios.core.data.repository.network

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import ru.resodostudios.core.data.repository.SearchRepository
import ru.resodostudios.flick.core.model.data.SearchMovie
import ru.resodostudios.flick.core.model.data.SearchPerson
import ru.resodostudios.flick.core.network.Dispatcher
import ru.resodostudios.flick.core.network.FlickDispatchers.IO
import ru.resodostudios.flick.core.network.FlickNetworkDataSource
import ru.resodostudios.flick.core.network.model.asExternalModel
import javax.inject.Inject

class NetworkSearchRepository @Inject constructor(
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher,
    private val datasource: FlickNetworkDataSource
) : SearchRepository {

    override fun searchMovies(query: String): Flow<List<SearchMovie>> = flow {
        emit(
            datasource
                .searchMovies(query)
                .map { it.asExternalModel() }
        )
    }.flowOn(ioDispatcher)
}