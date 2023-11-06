package ru.resodostudios.flick.core.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.resodostudios.flick.core.model.data.KnownFor
import ru.resodostudios.flick.core.model.data.Person

@Serializable
data class NetworkPerson(
    val adult: Boolean,
    val gender: Int,
    val id: Int,
    @SerialName("known_for")
    val knownFor: List<NetworkKnownFor>,
    @SerialName("known_for_department")
    val knownForDepartment: String,
    val name: String,
    @SerialName("original_name")
    val originalName: String,
    val popularity: Double,
    @SerialName("profile_path")
    val profilePath: String
)

fun NetworkPerson.asExternalModel() = Person(
    adult = adult,
    gender = gender,
    id = id,
    knownFor = knownFor.map {
        KnownFor(
            adult = it.adult,
            backdropPath = it.backdropPath,
            firstAirDate = it.firstAirDate,
            genreIds = it.genreIds,
            id = it.id,
            mediaType = it.mediaType,
            name = it.name,
            originCountry = it.originCountry,
            originalLanguage = it.originalLanguage,
            originalName = it.originalName,
            originalTitle = it.originalTitle,
            overview = it.overview,
            popularity = it.popularity,
            posterPath = it.posterPath,
            releaseDate = it.releaseDate,
            title = it.title,
            video = it.video,
            voteAverage = it.voteAverage,
            voteCount = it.voteCount
        )
    },
    knownForDepartment = knownForDepartment,
    name = name,
    originalName = originalName,
    popularity = popularity,
    profilePath = profilePath
)