package ru.resodostudios.movies.feature.movies.domain.use_case

import ru.resodostudios.movies.feature.movies.domain.repository.MoviesRepository
import javax.inject.Inject

class GetMoviesUseCase @Inject constructor(
    private val repository: MoviesRepository
) {

    suspend operator fun invoke() = repository.getMovies()
}