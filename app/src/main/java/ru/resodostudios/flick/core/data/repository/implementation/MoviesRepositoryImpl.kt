package ru.resodostudios.flick.core.data.repository.implementation

import okhttp3.ResponseBody.Companion.toResponseBody
import okio.IOException
import retrofit2.HttpException
import retrofit2.Response
import ru.resodostudios.flick.core.data.repository.MoviesRepository
import ru.resodostudios.flick.core.network.FlickApi
import ru.resodostudios.flick.core.network.model.Cast
import ru.resodostudios.flick.core.network.model.Crew
import ru.resodostudios.flick.core.network.model.Movie
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(
    private val apiRepository: FlickApi
) : MoviesRepository {

    override suspend fun getMovies(): Response<List<Movie>> {
        val response = try {
            apiRepository.getMovies()
        } catch (e: HttpException) {
            return Response.error(e.code(), e.message?.toResponseBody()!!)
        } catch(e: IOException) {
            return Response.error(e.hashCode(), e.message?.toResponseBody()!!)
        }
        return Response.success(response.body())
    }

    override suspend fun getMovie(id: Int): Response<Movie> {
        val response = try {
            apiRepository.getMovie(id)
        } catch (e: HttpException) {
            return Response.error(e.code(), e.message!!.toResponseBody())
        } catch(e: IOException) {
            return Response.error(e.hashCode(), e.message?.toResponseBody()!!)
        }
        return Response.success(response.body())
    }

    override suspend fun getCast(id: Int): Response<List<Cast>> {
        val response = try {
            apiRepository.getCast(id)
        } catch (e: HttpException) {
            return Response.error(e.code(), e.message!!.toResponseBody())
        } catch(e: IOException) {
            return Response.error(e.hashCode(), e.message?.toResponseBody()!!)
        }
        return Response.success(response.body())
    }

    override suspend fun getCrew(id: Int): Response<List<Crew>> {
        val response = try {
            apiRepository.getCrew(id)
        } catch (e: HttpException) {
            return Response.error(e.code(), e.message!!.toResponseBody())
        } catch(e: IOException) {
            return Response.error(e.hashCode(), e.message?.toResponseBody()!!)
        }
        return Response.success(response.body())
    }
}