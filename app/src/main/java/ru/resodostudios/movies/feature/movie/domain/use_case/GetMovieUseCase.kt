package ru.resodostudios.movies.feature.movie.domain.use_case

import ru.resodostudios.movies.feature.movies.data.repository.MoviesRepositoryImpl
import javax.inject.Inject

class GetMovieUseCase @Inject constructor(
    private val repository: MoviesRepositoryImpl
) {

    suspend operator fun invoke(id: Int) = repository.getMovie(id)
}