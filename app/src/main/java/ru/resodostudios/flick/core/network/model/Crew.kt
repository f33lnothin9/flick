package ru.resodostudios.flick.core.network.model

import kotlinx.serialization.Serializable

@Serializable
data class Crew(
    val person: NetworkPerson? = NetworkPerson(),
    val type: String? = null
)