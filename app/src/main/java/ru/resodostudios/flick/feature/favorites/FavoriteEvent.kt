package ru.resodostudios.flick.feature.favorites

import ru.resodostudios.flick.core.model.data.FavoriteMovie
import ru.resodostudios.flick.core.model.data.Movie

sealed interface FavoriteEvent {

    data class AddMovie(val movie: Movie) : FavoriteEvent
    data class DeleteMovie(val movie: FavoriteMovie) : FavoriteEvent
}