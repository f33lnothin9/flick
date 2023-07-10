package ru.resodostudios.movies.feature.favorites.domain.util

import ru.resodostudios.movies.feature.favorites.domain.model.FavoriteMovie
import ru.resodostudios.movies.feature.movie.data.model.Movie

sealed interface FavoriteEvent {

    data class AddMovie(val movie: Movie) : FavoriteEvent
    data class DeleteMovie(val movie: FavoriteMovie) : FavoriteEvent
}