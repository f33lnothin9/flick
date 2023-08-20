package ru.resodostudios.flick.feature.search.domain.use_case

import ru.resodostudios.flick.core.data.repository.SearchRepository
import javax.inject.Inject

class SearchMoviesUseCase @Inject constructor(
    private val repository: SearchRepository
) {

    suspend operator fun invoke(query: String) = repository.searchMovies(query)
}