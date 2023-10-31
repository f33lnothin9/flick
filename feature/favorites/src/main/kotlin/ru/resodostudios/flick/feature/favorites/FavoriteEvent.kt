package ru.resodostudios.flick.feature.favorites

import ru.resodostudios.flick.core.model.data.FavoriteMovie
import ru.resodostudios.flick.core.model.data.FavoritePerson
import ru.resodostudios.flick.core.model.data.Movie
import ru.resodostudios.flick.core.model.data.Person

sealed interface FavoriteEvent {

    data class AddMovie(val movie: Movie) : FavoriteEvent
    data class DeleteMovie(val movie: FavoriteMovie) : FavoriteEvent

    data class AddPerson(val person: Person) : FavoriteEvent
    data class DeletePerson(val person: FavoritePerson) : FavoriteEvent
}