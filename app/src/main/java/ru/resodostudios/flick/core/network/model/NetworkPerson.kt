package ru.resodostudios.flick.core.network.model

import kotlinx.serialization.Serializable
import ru.resodostudios.flick.core.model.data.Country
import ru.resodostudios.flick.core.model.data.Image
import ru.resodostudios.flick.core.model.data.Person

@Serializable
data class NetworkPerson(
    val birthday: String = "",
    val country: NetworkCountry = NetworkCountry(),
    val deathday: String = "",
    val gender: String = "",
    val id: Int = 0,
    val image: NetworkImage = NetworkImage(),
    val name: String = ""
)

fun NetworkPerson.asExternalModel() = Person(
    id = id,
    birthday = birthday,
    country = Country(
        name = country.name,
        code = country.code,
        timezone = country.timezone
    ),
    gender = gender,
    deathday = deathday,
    image = Image(
        medium = image.medium,
        original = image.original
    ),
    name = name
)