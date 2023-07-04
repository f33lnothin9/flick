package ru.resodostudios.movies.feature.movie.data.model

import kotlinx.serialization.Serializable
import ru.resodostudios.movies.core.data.model.Image
import ru.resodostudios.movies.core.data.model.Network
import ru.resodostudios.movies.core.data.model.Rating
import ru.resodostudios.movies.core.data.model.Schedule

@Serializable
data class Movie(
    val averageRuntime: Int? = null,
    val ended: String? = null,
    val genres: List<String>? = null,
    val id: Int? = null,
    val image: Image? = null,
    val language: String? = null,
    val name: String? = null,
    val network: Network? = null,
    val officialSite: String? = null,
    val premiered: String? = null,
    val rating: Rating? = null,
    val runtime: Int? = null,
    val schedule: Schedule? = null,
    val status: String? = null,
    val summary: String? = null
)