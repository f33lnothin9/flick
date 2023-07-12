package ru.resodostudios.movies.feature.search.domain.use_case

import ru.resodostudios.movies.core.data.repository.MoviesApiRepository
import javax.inject.Inject

class SearchMoviesUseCase @Inject constructor(
    private val repository: MoviesApiRepository
) {

    suspend operator fun invoke(query: String) = repository.searchMovies(query)
}