package ru.resodostudios.flick.feature.people.domain.repository

import retrofit2.Response
import ru.resodostudios.flick.feature.people.domain.model.Person

interface PeopleRepository {

    suspend fun getPeople(): Response<List<Person>>
}