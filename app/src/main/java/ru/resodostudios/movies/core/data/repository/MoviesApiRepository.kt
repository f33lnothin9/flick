package ru.resodostudios.movies.core.data.repository

import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response
import ru.resodostudios.movies.core.data.network.MoviesApi
import ru.resodostudios.movies.feature.movie.data.model.Movie
import ru.resodostudios.movies.feature.movies.data.model.MovieEntry
import ru.resodostudios.movies.feature.search.data.model.SearchedMovie
import javax.inject.Inject

class MoviesApiRepository @Inject constructor(
    private val apiRepository: MoviesApi
) {

    suspend fun getMovies(): Response<List<MovieEntry>> {
        val response = try {
            apiRepository.getMovies()
        } catch (e: Exception) {
            return Response.error(e.hashCode(), e.message?.toResponseBody()!!)
        }
        return Response.success(response.body())
    }

    suspend fun getMovie(id: Int): Response<Movie> {
        val response = try {
            apiRepository.getMovie(id)
        } catch (e: Exception) {
            return Response.error(e.hashCode(), e.message?.toResponseBody()!!)
        }
        return Response.success(response.body())
    }

    suspend fun searchMovies(query: String): Response<List<SearchedMovie>> {
        val response = try {
            apiRepository.searchMovies(query)
        } catch (e: Exception) {
            return Response.error(e.hashCode(), e.message?.toResponseBody()!!)
        }
        return Response.success(response.body())
    }
}