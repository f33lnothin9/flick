package ru.resodostudios.flick.core.data.repository

import kotlinx.coroutines.flow.Flow
import ru.resodostudios.flick.core.model.data.Person

interface PeopleRepository {

    fun getPeople(): Flow<List<Person>>
}