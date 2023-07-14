package ru.resodostudios.flick.feature.search.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.resodostudios.flick.feature.movies.data.model.MovieEntry

@Serializable
data class SearchedMovie(
    @SerialName("show")
    val movie: MovieEntry? = null
)