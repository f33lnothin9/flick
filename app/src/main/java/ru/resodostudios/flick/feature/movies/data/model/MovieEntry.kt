package ru.resodostudios.flick.feature.movies.data.model

import kotlinx.serialization.Serializable
import ru.resodostudios.flick.core.network.model.Image
import ru.resodostudios.flick.core.network.model.Rating

@Serializable
data class MovieEntry(
    val genres: List<String>? = null,
    val id: Int? = null,
    val image: Image? = null,
    val premiered: String? = null,
    val name: String? = null,
    val rating: Rating? = null,
    val runtime: Int? = null
)