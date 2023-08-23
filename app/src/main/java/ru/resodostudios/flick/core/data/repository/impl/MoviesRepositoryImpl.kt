package ru.resodostudios.flick.core.data.repository.impl

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.ResponseBody.Companion.toResponseBody
import okio.IOException
import retrofit2.HttpException
import retrofit2.Response
import ru.resodostudios.flick.core.common.network.Dispatcher
import ru.resodostudios.flick.core.common.network.FlickDispatchers
import ru.resodostudios.flick.core.data.repository.MoviesRepository
import ru.resodostudios.flick.core.network.FlickNetworkDataSource
import ru.resodostudios.flick.core.network.model.Cast
import ru.resodostudios.flick.core.network.model.Crew
import ru.resodostudios.flick.core.network.model.Movie
import ru.resodostudios.flick.core.network.model.asExternalModel
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(
    @Dispatcher(FlickDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    private val datasource: FlickNetworkDataSource
) : MoviesRepository {

    override fun getMovies(): Flow<List<ru.resodostudios.flick.core.model.data.Movie>> = flow {
        emit(
            datasource
                .getMovies()
                .map { it.asExternalModel() }
        )
    }.flowOn(ioDispatcher)

    override suspend fun getMovie(id: Int): Response<Movie> {
        val response = try {
            datasource.getMovie(id)
        } catch (e: HttpException) {
            return Response.error(e.code(), e.message!!.toResponseBody())
        } catch (e: IOException) {
            return Response.error(e.hashCode(), e.message?.toResponseBody()!!)
        }
        return Response.success(response.body())
    }

    override suspend fun getCast(id: Int): Response<List<Cast>> {
        val response = try {
            datasource.getCast(id)
        } catch (e: HttpException) {
            return Response.error(e.code(), e.message!!.toResponseBody())
        } catch (e: IOException) {
            return Response.error(e.hashCode(), e.message?.toResponseBody()!!)
        }
        return Response.success(response.body())
    }

    override suspend fun getCrew(id: Int): Response<List<Crew>> {
        val response = try {
            datasource.getCrew(id)
        } catch (e: HttpException) {
            return Response.error(e.code(), e.message!!.toResponseBody())
        } catch (e: IOException) {
            return Response.error(e.hashCode(), e.message?.toResponseBody()!!)
        }
        return Response.success(response.body())
    }
}