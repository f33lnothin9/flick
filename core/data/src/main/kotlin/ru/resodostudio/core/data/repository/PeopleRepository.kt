package ru.resodostudio.core.data.repository

import kotlinx.coroutines.flow.Flow
import ru.resodostudio.flick.core.model.data.Person

interface PeopleRepository {

    fun getPeople(): Flow<List<Person>>

    fun getPerson(id: Int): Flow<Person>
}