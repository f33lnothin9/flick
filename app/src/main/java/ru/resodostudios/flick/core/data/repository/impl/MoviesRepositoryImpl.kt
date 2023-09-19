package ru.resodostudios.flick.core.data.repository.impl

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import ru.resodostudios.flick.core.common.network.Dispatcher
import ru.resodostudios.flick.core.common.network.FlickDispatchers
import ru.resodostudios.flick.core.data.repository.MoviesRepository
import ru.resodostudios.flick.core.model.data.Cast
import ru.resodostudios.flick.core.model.data.Crew
import ru.resodostudios.flick.core.model.data.Movie
import ru.resodostudios.flick.core.network.FlickNetworkDataSource
import ru.resodostudios.flick.core.network.model.asExternalModel
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(
    @Dispatcher(FlickDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    private val datasource: FlickNetworkDataSource
) : MoviesRepository {

    override fun getMovies(): Flow<List<Movie>> = flow {
        emit(
            datasource
                .getMovies()
                .map { it.asExternalModel() }
        )
    }.flowOn(ioDispatcher)

    override fun getMovie(id: Int): Flow<Movie> = flow {
        emit(
            datasource.getMovie(id).asExternalModel()
        )
    }.flowOn(ioDispatcher)

    override fun getCast(id: Int): Flow<List<Cast>> = flow {
        emit(
            datasource
                .getCast(id)
                .map { it.asExternalModel() }
        )
    }.flowOn(ioDispatcher)

    override fun getCrew(id: Int): Flow<List<Crew>> = flow {
        emit(
            datasource
                .getCrew(id)
                .map { it.asExternalModel() }
        )
    }.flowOn(ioDispatcher)
}