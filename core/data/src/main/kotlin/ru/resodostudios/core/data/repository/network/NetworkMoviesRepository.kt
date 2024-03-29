package ru.resodostudios.core.data.repository.network

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import ru.resodostudios.core.data.repository.MoviesRepository
import ru.resodostudios.flick.core.model.data.Cast
import ru.resodostudios.flick.core.model.data.Crew
import ru.resodostudios.flick.core.model.data.ImageExtended
import ru.resodostudios.flick.core.model.data.Movie
import ru.resodostudios.flick.core.network.Dispatcher
import ru.resodostudios.flick.core.network.FlickDispatchers.IO
import ru.resodostudios.flick.core.network.FlickNetworkDataSource
import ru.resodostudios.flick.core.network.model.asExternalModel
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