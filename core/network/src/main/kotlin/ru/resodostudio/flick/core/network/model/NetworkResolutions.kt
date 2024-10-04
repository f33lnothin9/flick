package ru.resodostudio.flick.core.network.model

import kotlinx.serialization.Serializable

@Serializable
data class NetworkResolutions(
    val medium: NetworkResolution = NetworkResolution(),
    val original: NetworkResolution = NetworkResolution()
)