package ru.resodostudios.flick.feature.search.data.model

import kotlinx.serialization.Serializable
import ru.resodostudios.flick.core.network.model.NetworkPerson

@Serializable
data class SearchedPeople(
    val person: NetworkPerson? = null
)