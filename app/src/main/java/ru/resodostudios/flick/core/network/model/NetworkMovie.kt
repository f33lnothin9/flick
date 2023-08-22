package ru.resodostudios.flick.core.network.model

import kotlinx.serialization.Serializable

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