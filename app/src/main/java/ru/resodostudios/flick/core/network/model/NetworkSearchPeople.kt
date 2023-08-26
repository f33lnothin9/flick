package ru.resodostudios.flick.core.network.model

import kotlinx.serialization.Serializable
import ru.resodostudios.flick.core.model.data.SearchPeople

@Serializable
data class NetworkSearchPeople(
    val person: NetworkPerson = NetworkPerson()
)

fun NetworkSearchPeople.asExternalModel() = SearchPeople(
    person = NetworkPerson().asExternalModel()
)