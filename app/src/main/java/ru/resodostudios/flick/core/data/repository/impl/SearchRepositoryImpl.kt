package ru.resodostudios.flick.core.data.repository.impl

import okhttp3.ResponseBody.Companion.toResponseBody
import okio.IOException
import retrofit2.HttpException
import retrofit2.Response
import ru.resodostudios.flick.core.data.repository.SearchRepository
import ru.resodostudios.flick.core.network.FlickNetworkDataSource
import ru.resodostudios.flick.feature.search.data.model.SearchedMovie
import ru.resodostudios.flick.feature.search.data.model.SearchedPeople
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val datasource: FlickNetworkDataSource
) : SearchRepository {

    override suspend fun searchMovies(query: String): Response<List<SearchedMovie>> {
        val response = try {
            datasource.searchMovies(query)
        } catch (e: HttpException) {
            return Response.error(e.code(), e.message?.toResponseBody()!!)
        } catch(e: IOException) {
            return Response.error(e.hashCode(), e.message?.toResponseBody()!!)
        }
        return Response.success(response.body())
    }

    override suspend fun searchPeople(query: String): Response<List<SearchedPeople>> {
        val response = try {
            datasource.searchPeople(query)
        } catch (e: HttpException) {
            return Response.error(e.code(), e.message?.toResponseBody()!!)
        } catch(e: IOException) {
            return Response.error(e.hashCode(), e.message?.toResponseBody()!!)
        }
        return Response.success(response.body())
    }
}