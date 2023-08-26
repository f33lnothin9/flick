package ru.resodostudios.flick.core.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.resodostudios.flick.core.model.data.SearchMovie

@Serializable
data class NetworkSearchMovie(
    @SerialName("show")
    val movie: NetworkMovie? = null
)

fun NetworkSearchMovie.asExternalModel() = SearchMovie(
    movie = NetworkMovie().asExternalModel()
)