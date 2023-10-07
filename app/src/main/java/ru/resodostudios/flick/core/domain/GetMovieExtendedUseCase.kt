package ru.resodostudios.flick.core.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import ru.resodostudios.flick.core.data.repository.FavoritesRepository
import ru.resodostudios.flick.core.data.repository.MoviesRepository
import ru.resodostudios.flick.core.model.data.MovieExtended
import javax.inject.Inject

class GetMovieExtendedUseCase @Inject constructor(
    private val moviesRepository: MoviesRepository,
    private val favoritesRepository: FavoritesRepository
) {

    operator fun invoke(id: Int): Flow<MovieExtended> {
        return combine(
            moviesRepository.getMovie(id),
            moviesRepository.getCast(id),
            moviesRepository.getCrew(id),
            moviesRepository.getMovieImages(id),
            favoritesRepository.getMovies()
        ) { movie, cast, crew, images, favoriteMovies ->
            MovieExtended(
                movie = movie,
                cast = cast,
                crew = crew,
                images = images,
                isFavorite = favoriteMovies.any { it.id == movie.id }
            )
        }
    }
}