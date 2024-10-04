package ru.resodostudio.flick.core.model.data

data class Movie(
    val id: Int,
    val averageRuntime: Int,
    val ended: String,
    val genres: List<String>,
    val image: Image,
    val language: String,
    val name: String,
    val network: Network,
    val officialSite: String,
    val premiered: String,
    val rating: Rating,
    val runtime: Int,
    val status: String,
    val summary: String
)