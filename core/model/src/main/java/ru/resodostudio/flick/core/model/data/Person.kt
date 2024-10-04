package ru.resodostudio.flick.core.model.data

data class Person(
    val adult: Boolean,
    val gender: Int,
    val id: Int,
    val knownFor: List<KnownFor>,
    val knownForDepartment: String,
    val name: String,
    val originalName: String,
    val popularity: Double,
    val profilePath: String
)