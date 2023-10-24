package ru.resodostudios.flick.core.network.model

import kotlinx.serialization.Serializable
import ru.resodostudios.flick.core.model.data.Country
import ru.resodostudios.flick.core.model.data.Image
import ru.resodostudios.flick.core.model.data.Person
import ru.resodostudios.flick.core.model.data.SearchPerson

@Serializable
data class NetworkSearchPeople(
    val person: NetworkPerson = NetworkPerson()
)

fun NetworkSearchPeople.asExternalModel() = SearchPerson(
    person = Person(
        birthday = person.birthday,
        country = Country(
            name = person.country.name,
            code = person.country.code,
            timezone = person.country.timezone
        ),
        deathday = person.deathday,
        gender = person.gender,
        id = person.id,
        image = Image(
            medium = person.image.medium,
            original = person.image.original
        ),
        name = person.name
    )
)