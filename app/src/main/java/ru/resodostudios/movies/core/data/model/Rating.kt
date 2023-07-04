package ru.resodostudios.movies.core.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Rating(
    val average: Double? = null
)