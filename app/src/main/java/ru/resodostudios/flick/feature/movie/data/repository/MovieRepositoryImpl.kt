package ru.resodostudios.flick.feature.movie.data.repository

import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response
import ru.resodostudios.flick.core.data.network.FlickApi
import ru.resodostudios.flick.feature.movie.data.model.Cast
import ru.resodostudios.flick.feature.movie.data.model.Movie
import ru.resodostudios.flick.feature.movie.domain.repository.MovieRepository
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val apiRepository: FlickApi
) : MovieRepository {

    override suspend fun getMovie(id: Int): Response<Movie> {
        val response = try {
            apiRepository.getMovie(id)
        } catch (e: Exception) {
            return Response.error(0, e.message?.toResponseBody()!!)
        }
        return Response.success(response.body())
    }

    override suspend fun getCast(id: Int): Response<List<Cast>> {
        val response = try {
            apiRepository.getCast(id)
        } catch (e: Exception) {
            return Response.error(0, e.message?.toResponseBody()!!)
        }
        return Response.success(response.body())
    }
}