package ru.resodostudios.flick.feature.favorites.domain.util

import ru.resodostudios.flick.core.model.data.FavoriteMovie
import ru.resodostudios.flick.core.network.model.Movie

sealed interface FavoriteEvent {

    data class AddMovie(val movie: Movie) : FavoriteEvent
    data class DeleteMovie(val movie: FavoriteMovie) : FavoriteEvent
}