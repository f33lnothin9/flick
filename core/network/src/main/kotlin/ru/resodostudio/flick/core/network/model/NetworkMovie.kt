package ru.resodostudio.flick.core.network.model

import kotlinx.serialization.Serializable
import ru.resodostudio.flick.core.model.data.Country
import ru.resodostudio.flick.core.model.data.Image
import ru.resodostudio.flick.core.model.data.Movie
import ru.resodostudio.flick.core.model.data.Network
import ru.resodostudio.flick.core.model.data.Rating

@Serializable
data class NetworkMovie(
    val id: Int = -1,
    val averageRuntime: Int = 0,
    val ended: String = "",
    val genres: List<String> = emptyList(),
    val image: NetworkImage = NetworkImage(),
    val language: String = "",
    val name: String = "",
    val network: NetworkNetwork = NetworkNetwork(),
    val officialSite: String = "",
    val premiered: String = "",
    val rating: NetworkRating = NetworkRating(),
    val runtime: Int = 0,
    val status: String = "",
    val summary: String = ""
)

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