package ru.resodostudios.movies.feature.movies.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Schedule(
    val days: List<String>? = null,
    val time: String? = null
)