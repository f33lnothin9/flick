package ru.resodostudios.movies.feature.search.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.resodostudios.movies.feature.movies.data.model.MovieEntry

@Serializable
data class SearchedMovie(
    @SerialName("show")
    val movie: MovieEntry? = null
)