package ru.resodostudios.movies.feature.movie.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Country(
    val name: String? = null,
    val code: String? = null,
    val timezone: String? = null
)