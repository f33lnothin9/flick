package ru.resodostudios.flick.core.model.data

data class MovieExtended(
    val movie: Movie,
    val images: List<ImageExtended>,
    val isFavorite: Boolean
)