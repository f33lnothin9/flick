package ru.resodostudios.flick.feature.search.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.resodostudios.flick.core.network.model.Movie

@Serializable
data class SearchedMovie(
    @SerialName("show")
    val movie: Movie? = null
)