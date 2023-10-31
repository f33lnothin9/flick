package ru.resodostudios.flick.core.model.data

data class PersonExtended(
    val person: Person,
    val castCredits: List<CastCredits>,
    val crewCredits: List<CrewCredits>,
    val isFavorite: Boolean
)