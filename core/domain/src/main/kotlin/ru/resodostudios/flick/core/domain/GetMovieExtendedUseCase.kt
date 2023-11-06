package ru.resodostudios.flick.core.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import ru.resodostudios.core.data.repository.MoviesRepository
import ru.resodostudios.flick.core.model.data.MovieExtended
import javax.inject.Inject

class GetMovieExtendedUseCase @Inject constructor(
    private val moviesRepository: MoviesRepository
) {

    operator fun invoke(id: Int): Flow<MovieExtended> {
        return combine(
            moviesRepository.getMovie(id),
            moviesRepository.getMovieImages(id)
        ) { movie, images ->
            MovieExtended(
                movie = movie,
                images = images,
                isFavorite = false
            )
        }
    }
}