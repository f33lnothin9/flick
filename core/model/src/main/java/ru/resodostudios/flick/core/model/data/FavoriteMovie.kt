package ru.resodostudios.flick.core.model.data

data class FavoriteMovie(
    val id: Int,
    val image: String,
    val name: String,
    val rating: Double?,
    val genres: List<String>?
)