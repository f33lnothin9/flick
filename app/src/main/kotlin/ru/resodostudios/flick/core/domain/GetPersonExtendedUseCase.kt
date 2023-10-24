package ru.resodostudios.flick.core.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import ru.resodostudios.flick.core.data.repository.FavoritesRepository
import ru.resodostudios.flick.core.data.repository.PeopleRepository
import ru.resodostudios.flick.core.model.data.PersonExtended
import javax.inject.Inject

class GetPersonExtendedUseCase @Inject constructor(
    private val peopleRepository: PeopleRepository,
    private val favoritesRepository: FavoritesRepository
) {

    operator fun invoke(id: Int): Flow<PersonExtended> {
        return combine(
            peopleRepository.getPerson(id),
            peopleRepository.getCastCredits(id),
            peopleRepository.getCrewCredits(id),
            favoritesRepository.getPeople()
        ) { person, castCredits, crewCredits, favoritePeople ->
            PersonExtended(
                person = person,
                castCredits = castCredits,
                crewCredits = crewCredits,
                isFavorite = favoritePeople.any { it.id == person.id }
            )
        }
    }
}