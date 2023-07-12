package ru.resodostudios.movies.feature.movies.domain.use_case

import ru.resodostudios.movies.core.data.repository.MoviesApiRepository
import javax.inject.Inject

class GetMoviesUseCase @Inject constructor(
    private val repository: MoviesApiRepository
) {

    suspend operator fun invoke() = repository.getMovies()
}