package ru.resodostudios.flick.core.network.model

import kotlinx.serialization.Serializable

@Serializable
data class Movie(
    val id: Int = 0,
    val averageRuntime: Int? = null,
    val ended: String? = null,
    val genres: List<String>? = null,
    val image: Image? = null,
    val language: String? = null,
    val name: String = "",
    val network: Network? = null,
    val officialSite: String? = null,
    val premiered: String? = null,
    val rating: Rating? = null,
    val runtime: Int? = null,
    val status: String? = null,
    val summary: String? = null
)