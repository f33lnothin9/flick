package ru.resodostudios.flick.core.model.data

data class MovieExtended(
    val movie: Movie,
    val cast: List<Cast>,
    val crew: List<Crew>,
    val isFavorite: Boolean
)