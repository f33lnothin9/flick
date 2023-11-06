package ru.resodostudios.core.data.repository.network

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import ru.resodostudios.core.data.repository.PeopleRepository
import ru.resodostudios.flick.core.model.data.CastCredits
import ru.resodostudios.flick.core.model.data.CrewCredits
import ru.resodostudios.flick.core.model.data.Person
import ru.resodostudios.flick.core.network.Dispatcher
import ru.resodostudios.flick.core.network.FlickDispatchers.IO
import ru.resodostudios.flick.core.network.FlickNetworkDataSource
import ru.resodostudios.flick.core.network.model.asExternalModel
import javax.inject.Inject

class NetworkPeopleRepository @Inject constructor(
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher,
    private val datasource: FlickNetworkDataSource
) : PeopleRepository {

    override fun getPeople(): Flow<List<Person>> = flow {
        emit(
            datasource
                .getPeople()
                .map { it.asExternalModel() }
        )
    }.flowOn(ioDispatcher)

    override fun getPerson(id: Int): Flow<Person> = flow {
        emit(
            datasource.getPerson(id).asExternalModel()
        )
    }.flowOn(ioDispatcher)
}