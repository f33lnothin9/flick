package ru.resodostudios.flick.core.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import ru.resodostudios.core.data.repository.SearchRepository
import ru.resodostudios.flick.core.model.data.SearchResult
import javax.inject.Inject

class GetSearchContentsUseCase @Inject constructor(
    private val searchRepository: SearchRepository
) {

    operator fun invoke(query: String): Flow<SearchResult> {
        return combine(
            searchRepository.searchMovies(query),
            searchRepository.searchPeople(query)
        ) { movies, people ->
            SearchResult(
                movies = movies,
                people = people
            )
        }
    }
}