package ru.resodostudios.flick.core.data.repository.impl

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import ru.resodostudios.flick.core.common.network.Dispatcher
import ru.resodostudios.flick.core.common.network.FlickDispatchers.IO
import ru.resodostudios.flick.core.data.repository.PeopleRepository
import ru.resodostudios.flick.core.model.data.Country
import ru.resodostudios.flick.core.model.data.Image
import ru.resodostudios.flick.core.model.data.Person
import ru.resodostudios.flick.core.network.FlickNetworkDataSource
import javax.inject.Inject

class PeopleRepositoryImpl @Inject constructor(
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher,
    private val datasource: FlickNetworkDataSource
) : PeopleRepository {

    override fun getPeople(): Flow<List<Person>> = flow {
        emit(
            datasource
                .getPeople()
                .map {
                    Person(
                        id = it.id,
                        birthday = it.birthday,
                        country = Country(
                            name = it.country.name,
                            code = it.country.code,
                            timezone = it.country.timezone
                        ),
                        gender = it.gender,
                        deathday = it.deathday,
                        image = Image(
                            medium = it.image.medium,
                            original = it.image.original
                        ),
                        name = it.name
                    )
                }
        )
    }.flowOn(ioDispatcher)
}