package ru.resodostudios.flick.core.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import ru.resodostudios.flick.core.data.repository.FavoritesRepository
import ru.resodostudios.flick.core.model.data.Favorites
import javax.inject.Inject

class GetFavoritesUseCase @Inject constructor(
    private val favoritesRepository: FavoritesRepository
) {

    operator fun invoke(): Flow<Favorites> {
        return combine(
            favoritesRepository.getMovies(),
            favoritesRepository.getPeople()
        ) { movies, people ->
            Favorites(
                movies = movies,
                people = people
            )
        }
    }
}