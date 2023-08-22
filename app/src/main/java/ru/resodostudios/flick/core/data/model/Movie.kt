package ru.resodostudios.flick.core.data.model

import ru.resodostudios.flick.core.model.data.Country
import ru.resodostudios.flick.core.model.data.Image
import ru.resodostudios.flick.core.model.data.Movie
import ru.resodostudios.flick.core.model.data.Network
import ru.resodostudios.flick.core.model.data.Rating
import ru.resodostudios.flick.core.network.model.NetworkMovie

fun NetworkMovie.asExternalModel() = Movie(
    id = id,
    name = name,
    ended = ended,
    language = language,
    officialSite = officialSite,
    premiered = premiered,
    runtime = runtime,
    status = status,
    summary = summary,
    genres = genres,
    image = Image(
        medium = image.medium,
        original = image.original
    ),
    network = Network(
        country = Country(
            name = network.country.name,
            code = network.country.code,
            timezone = network.country.timezone
        ),
        id = network.id,
        name = network.name,
        officialSite = network.officialSite
    ),
    averageRuntime = averageRuntime,
    rating = Rating(
        average = rating.average
    )
)