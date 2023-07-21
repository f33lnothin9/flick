package ru.resodostudios.flick.feature.people.domain.use_case

import ru.resodostudios.flick.feature.people.domain.repository.PeopleRepository
import javax.inject.Inject

class GetPeopleUseCase @Inject constructor(
    private val repository: PeopleRepository
) {

    suspend operator fun invoke() = repository.getPeople()
}