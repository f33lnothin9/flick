package ru.resodostudios.flick.feature.search.data.model

import kotlinx.serialization.Serializable
import ru.resodostudios.flick.feature.people.domain.model.People

@Serializable
data class SearchedPeople(
    val person: People? = null
)