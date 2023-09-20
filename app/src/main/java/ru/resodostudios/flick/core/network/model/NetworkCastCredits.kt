package ru.resodostudios.flick.core.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkCastCredits(
    @SerialName(value = "_embedded")
    val embedded: NetworkEmbedded = NetworkEmbedded()
)