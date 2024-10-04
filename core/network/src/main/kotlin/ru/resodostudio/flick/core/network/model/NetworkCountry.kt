package ru.resodostudio.flick.core.network.model

import kotlinx.serialization.Serializable

@Serializable
data class NetworkCountry(
    val name: String = "",
    val code: String = "",
    val timezone: String = ""
)