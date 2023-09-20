package ru.resodostudios.flick.core.network.model

import kotlinx.serialization.Serializable

@Serializable
data class NetworkEmbedded(
    val character: NetworkCharacter = NetworkCharacter(),
    val show: NetworkMovie = NetworkMovie()
)