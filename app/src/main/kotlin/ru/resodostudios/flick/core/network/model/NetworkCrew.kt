package ru.resodostudios.flick.core.network.model

import kotlinx.serialization.Serializable
import ru.resodostudios.flick.core.model.data.Country
import ru.resodostudios.flick.core.model.data.Crew
import ru.resodostudios.flick.core.model.data.Image
import ru.resodostudios.flick.core.model.data.Person

@Serializable
data class NetworkCrew(
    val person: NetworkPerson = NetworkPerson(),
    val type: String = ""
)

fun NetworkCrew.asExternalModel() = Crew(
    person = Person(
        id = person.id,
        birthday = person.birthday,
        country = Country(
            name = person.country.name,
            code = person.country.code,
            timezone = person.country.timezone
        ),
        gender = person.gender,
        deathday = person.deathday,
        image = Image(
            medium = person.image.medium,
            original = person.image.original
        ),
        name = person.name
    ),
    type = type
)