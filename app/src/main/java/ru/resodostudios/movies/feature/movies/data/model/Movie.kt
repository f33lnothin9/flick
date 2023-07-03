package ru.resodostudios.movies.feature.movies.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Movie(
    val averageRuntime: Int? = null,
    val dvdCountry: DvdCountry? = null,
    val ended: String? = null,
    val genres: List<String>? = null,
    val id: Int? = null,
    val image: Image? = null,
    val language: String? = null,
    val name: String? = null,
    val network: Network? = null,
    val officialSite: String? = null,
    val premiered: String? = null,
    val rating: Rating? = null,
    val runtime: Int? = null,
    val schedule: Schedule? = null,
    val status: String? = null,
    val summary: String? = null,
    val type: String? = null,
    val updated: Int? = null,
    val url: String? = null,
    val webChannel: WebChannel? = null,
    val weight: Int? = null
)