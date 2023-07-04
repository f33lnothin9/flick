package ru.resodostudios.movies.core.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Country(
    val name: String? = null,
    val code: String? = null,
    val timezone: String? = null
)