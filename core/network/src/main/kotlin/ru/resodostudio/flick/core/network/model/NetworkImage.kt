package ru.resodostudio.flick.core.network.model

import kotlinx.serialization.Serializable

@Serializable
data class NetworkImage(
    val medium: String = "",
    val original: String = ""
)