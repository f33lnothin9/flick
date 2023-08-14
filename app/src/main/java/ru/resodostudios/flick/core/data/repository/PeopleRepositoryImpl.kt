package ru.resodostudios.flick.core.data.repository

import okhttp3.ResponseBody.Companion.toResponseBody
import okio.IOException
import retrofit2.HttpException
import retrofit2.Response
import ru.resodostudios.flick.core.network.FlickApi
import ru.resodostudios.flick.feature.people.domain.model.Person
import ru.resodostudios.flick.feature.people.domain.repository.PeopleRepository
import javax.inject.Inject

class PeopleRepositoryImpl @Inject constructor(
    private val apiRepository: FlickApi
) : PeopleRepository {

    override suspend fun getPeople(): Response<List<Person>> {
        val response = try {
            apiRepository.getPeople()
        } catch (e: HttpException) {
            return Response.error(e.code(), e.message?.toResponseBody()!!)
        } catch(e: IOException) {
            return Response.error(e.hashCode(), e.message?.toResponseBody()!!)
        }
        return Response.success(response.body())
    }
}