package ru.resodostudios.movies.feature.movie.domain.use_case

import ru.resodostudios.movies.feature.movies.data.repository.MoviesApiRepository
import javax.inject.Inject

class GetMovieUseCase @Inject constructor(
    private val repository: MoviesApiRepository
) {

    suspend operator fun invoke(id: String) = repository.getMovie(id)
}