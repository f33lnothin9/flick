package ru.resodostudio.core.data.repository.network

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import ru.resodostudio.core.data.repository.MoviesRepository
import ru.resodostudio.flick.core.model.data.ImageExtended
import ru.resodostudio.flick.core.model.data.Movie
import ru.resodostudio.flick.core.network.Dispatcher
import ru.resodostudio.flick.core.network.FlickDispatchers.IO
import ru.resodostudio.flick.core.network.FlickNetworkDataSource
import ru.resodostudio.flick.core.network.model.asExternalModel
import javax.inject.Inject

class NetworkMoviesRepository @Inject constructor(
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher,
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

    override fun getMovieImages(id: Int): Flow<List<ImageExtended>> = flow {
        emit(
            datasource
                .getMovieImages(id)
                .map { it.asExternalModel() }
        )
    }.flowOn(ioDispatcher)
}