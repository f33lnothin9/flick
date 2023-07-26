package ru.resodostudios.flick.feature.people.data.repository

import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response
import ru.resodostudios.flick.core.data.network.FlickApi
import ru.resodostudios.flick.feature.people.domain.model.Person
import ru.resodostudios.flick.feature.people.domain.repository.PeopleRepository
import javax.inject.Inject

class PeopleRepositoryImpl @Inject constructor(
    private val apiRepository: FlickApi
) : PeopleRepository {

    override suspend fun getPeople(): Response<List<Person>> {
        val response = try {
            apiRepository.getPeople()
        } catch (e: Exception) {
            return Response.error(e.hashCode(), e.message?.toResponseBody()!!)
        }
        return Response.success(response.body())
    }
}