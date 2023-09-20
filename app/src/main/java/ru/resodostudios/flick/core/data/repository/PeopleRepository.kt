package ru.resodostudios.flick.core.data.repository

import kotlinx.coroutines.flow.Flow
import ru.resodostudios.flick.core.model.data.CastCredits
import ru.resodostudios.flick.core.model.data.Person

interface PeopleRepository {

    fun getPeople(): Flow<List<Person>>

    fun getPerson(id: Int): Flow<Person>

    fun getCastCredits(id: Int): Flow<List<CastCredits>>
}