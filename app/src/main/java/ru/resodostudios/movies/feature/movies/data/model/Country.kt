package ru.resodostudios.movies.feature.movies.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Country(
    val code: String? = null,
    val name: String? = null,
    val timezone: String? = null
)