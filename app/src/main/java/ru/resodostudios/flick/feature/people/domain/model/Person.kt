package ru.resodostudios.flick.feature.people.domain.model

import kotlinx.serialization.Serializable
import ru.resodostudios.flick.core.data.model.Country
import ru.resodostudios.flick.core.data.model.Image

@Serializable
data class Person(
    val birthday: String? = null,
    val country: Country? = null,
    val deathday: String? = null,
    val gender: String? = null,
    val id: Int? = null,
    val image: Image? = null,
    val name: String? = null
)