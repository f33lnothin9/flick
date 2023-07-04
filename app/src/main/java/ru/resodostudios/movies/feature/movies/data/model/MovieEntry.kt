package ru.resodostudios.movies.feature.movies.data.model

import kotlinx.serialization.Serializable

@Serializable
data class MovieEntry(
    val genres: List<String>? = null,
    val id: Int? = null,
    val image: Image? = null,
    val language: String? = null,
    val name: String? = null,
    val rating: Rating? = null,
    val runtime: Int? = null
)