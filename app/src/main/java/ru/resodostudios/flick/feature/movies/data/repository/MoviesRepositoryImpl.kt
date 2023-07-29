package ru.resodostudios.flick.feature.movies.data.repository

import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response
import ru.resodostudios.flick.core.data.network.FlickApi
import ru.resodostudios.flick.feature.movies.data.model.MovieEntry
import ru.resodostudios.flick.feature.movies.domain.repository.MoviesRepository
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(
    private val apiRepository: FlickApi
) : MoviesRepository {

    override suspend fun getMovies(): Response<List<MovieEntry>> {
        val response = try {
            apiRepository.getMovies()
        } catch (e: Exception) {
            return Response.error(0, e.message?.toResponseBody()!!)
        }
        return Response.success(response.body())
    }
}