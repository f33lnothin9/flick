package ru.resodostudios.flick.core.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.resodostudios.flick.core.data.repository.PeopleRepository
import ru.resodostudios.flick.core.model.data.PersonExtended
import javax.inject.Inject

class GetPersonExtendedUseCase @Inject constructor(
    private val peopleRepository: PeopleRepository
) {

    operator fun invoke(id: Int): Flow<PersonExtended> {
        return peopleRepository.getPerson(id).map { person ->
            PersonExtended(
                person = person
            )
        }
    }
}