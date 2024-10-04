package ru.resodostudio.flick.core.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkEmbedded(
    val character: NetworkCharacter = NetworkCharacter(),
    @SerialName(value = "show")
    val movie: NetworkMovie = NetworkMovie()
)