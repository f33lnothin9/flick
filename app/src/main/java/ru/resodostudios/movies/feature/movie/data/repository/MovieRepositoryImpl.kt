package ru.resodostudios.movies.feature.movie.data.repository

import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response
import ru.resodostudios.movies.core.data.network.MoviesApi
import ru.resodostudios.movies.feature.movie.data.model.Movie
import ru.resodostudios.movies.feature.movie.domain.repository.MovieRepository
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val apiRepository: MoviesApi
) : MovieRepository {

    override suspend fun getMovie(id: Int): Response<Movie> {
        val response = try {
            apiRepository.getMovie(id)
        } catch (e: Exception) {
            return Response.error(e.hashCode(), e.message?.toResponseBody()!!)
        }
        return Response.success(response.body())
    }
}