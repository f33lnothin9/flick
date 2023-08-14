package ru.resodostudios.flick.core.data.repository

import okhttp3.ResponseBody.Companion.toResponseBody
import okio.IOException
import retrofit2.HttpException
import retrofit2.Response
import ru.resodostudios.flick.core.network.FlickApi
import ru.resodostudios.flick.feature.search.data.model.SearchedMovie
import ru.resodostudios.flick.feature.search.data.model.SearchedPeople
import ru.resodostudios.flick.feature.search.domain.repository.SearchRepository
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val apiRepository: FlickApi
) : SearchRepository {

    override suspend fun searchMovies(query: String): Response<List<SearchedMovie>> {
        val response = try {
            apiRepository.searchMovies(query)
        } catch (e: HttpException) {
            return Response.error(e.code(), e.message?.toResponseBody()!!)
        } catch(e: IOException) {
            return Response.error(e.hashCode(), e.message?.toResponseBody()!!)
        }
        return Response.success(response.body())
    }

    override suspend fun searchPeople(query: String): Response<List<SearchedPeople>> {
        val response = try {
            apiRepository.searchPeople(query)
        } catch (e: HttpException) {
            return Response.error(e.code(), e.message?.toResponseBody()!!)
        } catch(e: IOException) {
            return Response.error(e.hashCode(), e.message?.toResponseBody()!!)
        }
        return Response.success(response.body())
    }
}