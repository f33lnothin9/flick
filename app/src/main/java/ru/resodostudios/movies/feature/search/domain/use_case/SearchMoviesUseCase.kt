package ru.resodostudios.movies.feature.search.domain.use_case

import ru.resodostudios.movies.feature.search.domain.repository.SearchRepository
import javax.inject.Inject

class SearchMoviesUseCase @Inject constructor(
    private val repository: SearchRepository
) {

    suspend operator fun invoke(query: String) = repository.searchMovies(query)
}