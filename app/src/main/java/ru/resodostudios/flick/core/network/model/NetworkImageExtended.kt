package ru.resodostudios.flick.core.network.model

import kotlinx.serialization.Serializable

@Serializable
data class NetworkImageExtended(
    val id: Int = 0,
    val main: Boolean = false,
    val resolutions: NetworkResolutions,
    val type: String = ""
)