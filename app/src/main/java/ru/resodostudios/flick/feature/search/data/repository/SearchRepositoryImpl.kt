package ru.resodostudios.flick.feature.search.data.repository

import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response
import ru.resodostudios.flick.core.data.network.FlickApi
import ru.resodostudios.flick.feature.search.data.model.SearchedMovie
import ru.resodostudios.flick.feature.search.domain.repository.SearchRepository
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val apiRepository: FlickApi
) : SearchRepository {

    override suspend fun searchMovies(query: String): Response<List<SearchedMovie>> {
        val response = try {
            apiRepository.searchMovies(query)
        } catch (e: Exception) {
            return Response.error(e.hashCode(), e.message?.toResponseBody()!!)
        }
        return Response.success(response.body())
    }
}