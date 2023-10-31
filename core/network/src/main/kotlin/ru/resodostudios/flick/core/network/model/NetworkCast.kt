package ru.resodostudios.flick.core.network.model

import kotlinx.serialization.Serializable
import ru.resodostudios.flick.core.model.data.Cast
import ru.resodostudios.flick.core.model.data.Country
import ru.resodostudios.flick.core.model.data.Image
import ru.resodostudios.flick.core.model.data.Person
import ru.resodostudios.flick.core.model.data.Character

@Serializable
data class NetworkCast(
    val character: NetworkCharacter = NetworkCharacter(),
    val person: NetworkPerson = NetworkPerson()
)

fun NetworkCast.asExternalModel() = Cast(
    character = Character(
        id = character.id,
        image = Image(
            medium = character.image.medium,
            original = character.image.original
        ),
        name = character.name
    ),
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
    )
)