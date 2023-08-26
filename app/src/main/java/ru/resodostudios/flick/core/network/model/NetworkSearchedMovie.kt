package ru.resodostudios.flick.core.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.resodostudios.flick.core.network.model.NetworkMovie

@Serializable
data class NetworkSearchedMovie(
    @SerialName("show")
    val movie: NetworkMovie? = null
)