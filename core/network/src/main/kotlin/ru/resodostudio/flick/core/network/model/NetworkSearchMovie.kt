package ru.resodostudio.flick.core.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.resodostudio.flick.core.model.data.Country
import ru.resodostudio.flick.core.model.data.Image
import ru.resodostudio.flick.core.model.data.Movie
import ru.resodostudio.flick.core.model.data.Network
import ru.resodostudio.flick.core.model.data.Rating
import ru.resodostudio.flick.core.model.data.SearchMovie

@Serializable
data class NetworkSearchMovie(
    @SerialName("show")
    val movie: NetworkMovie = NetworkMovie()
)

fun NetworkSearchMovie.asExternalModel() = SearchMovie(
    movie = Movie(
        id = movie.id,
        name = movie.name,
        ended = movie.ended,
        language = movie.language,
        officialSite = movie.officialSite,
        premiered = movie.premiered,
        runtime = movie.runtime,
        status = movie.status,
        summary = movie.summary,
        genres = movie.genres,
        image = Image(
            medium = movie.image.medium,
            original = movie.image.original
        ),
        network = Network(
            country = Country(
                name = movie.network.country.name,
                code = movie.network.country.code,
                timezone = movie.network.country.timezone
            ),
            id = movie.network.id,
            name = movie.network.name,
            officialSite = movie.network.officialSite
        ),
        averageRuntime = movie.averageRuntime,
        rating = Rating(
            average = movie.rating.average
        )
    )
)