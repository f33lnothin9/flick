package ru.resodostudios.flick.core.network.model

import kotlinx.serialization.Serializable
import ru.resodostudios.flick.core.network.model.NetworkPerson

@Serializable
data class NetworkSearchedPeople(
    val person: NetworkPerson? = null
)