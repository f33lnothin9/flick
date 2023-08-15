package ru.resodostudios.flick.feature.movie.domain.use_case

import ru.resodostudios.flick.core.data.repository.MoviesRepository
import javax.inject.Inject

class GetCastUseCase @Inject constructor(
    private val repository: MoviesRepository
) {

    suspend operator fun invoke(id: Int) = repository.getCast(id)
}