package ru.resodostudios.flick.core.data.repository.impl

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import ru.resodostudios.flick.core.common.network.Dispatcher
import ru.resodostudios.flick.core.common.network.FlickDispatchers.IO
import ru.resodostudios.flick.core.data.repository.PeopleRepository
import ru.resodostudios.flick.core.model.data.CastCredits
import ru.resodostudios.flick.core.model.data.Person
import ru.resodostudios.flick.core.network.FlickNetworkDataSource
import ru.resodostudios.flick.core.network.model.asExternalModel
import javax.inject.Inject

class PeopleRepositoryImpl @Inject constructor(
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

    override fun getCastCredits(id: Int): Flow<List<CastCredits>> = flow {
        emit(
            datasource
                .getCastCredits(id)
                .map { it.asExternalModel() }
        )
    }.flowOn(ioDispatcher)
}