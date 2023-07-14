package ru.resodostudios.movies.feature.search.domain.use_case

import ru.resodostudios.movies.feature.movies.data.repository.MoviesRepositoryImpl
import javax.inject.Inject

class SearchMoviesUseCase @Inject constructor(
    private val repository: MoviesRepositoryImpl
) {

    suspend operator fun invoke(query: String) = repository.searchMovies(query)
}