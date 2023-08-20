package ru.resodostudios.flick.core.data.repository.impl

import okhttp3.ResponseBody.Companion.toResponseBody
import okio.IOException
import retrofit2.HttpException
import retrofit2.Response
import ru.resodostudios.flick.core.data.repository.PeopleRepository
import ru.resodostudios.flick.core.network.FlickNetworkDataSource
import ru.resodostudios.flick.feature.people.domain.model.Person
import javax.inject.Inject

class PeopleRepositoryImpl @Inject constructor(
    private val datasource: FlickNetworkDataSource
) : PeopleRepository {

    override suspend fun getPeople(): Response<List<Person>> {
        val response = try {
            datasource.getPeople()
        } catch (e: HttpException) {
            return Response.error(e.code(), e.message?.toResponseBody()!!)
        } catch(e: IOException) {
            return Response.error(e.hashCode(), e.message?.toResponseBody()!!)
        }
        return Response.success(response.body())
    }
}