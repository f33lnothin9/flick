package ru.resodostudios.flick.feature.search.domain.use_case

import ru.resodostudios.flick.feature.search.domain.repository.SearchRepository
import javax.inject.Inject

class SearchPeopleUseCase @Inject constructor(
    private val repository: SearchRepository
) {

    suspend operator fun invoke(query: String) = repository.searchPeople(query)
}