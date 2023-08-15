package ru.resodostudios.flick.feature.movies.domain.use_case

import ru.resodostudios.flick.core.data.repository.MoviesRepository
import javax.inject.Inject

class GetMoviesUseCase @Inject constructor(
    private val repository: MoviesRepository
) {

    suspend operator fun invoke() = repository.getMovies()
}