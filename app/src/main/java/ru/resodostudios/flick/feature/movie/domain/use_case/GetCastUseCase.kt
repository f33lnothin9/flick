package ru.resodostudios.flick.feature.movie.domain.use_case

import ru.resodostudios.flick.feature.movie.domain.repository.MovieRepository
import javax.inject.Inject

class GetCastUseCase @Inject constructor(
    private val repository: MovieRepository
) {

    suspend operator fun invoke(id: Int) = repository.getCast(id)
}