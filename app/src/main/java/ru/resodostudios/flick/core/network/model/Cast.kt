package ru.resodostudios.flick.core.network.model

import kotlinx.serialization.Serializable

@Serializable
data class Cast(
    val character: Character? = Character(),
    val person: NetworkPerson? = NetworkPerson()
)