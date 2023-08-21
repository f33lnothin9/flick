package ru.resodostudios.flick.core.network.model

import kotlinx.serialization.Serializable

@Serializable
data class NetworkPerson(
    val birthday: String = "",
    val country: NetworkCountry = NetworkCountry(),
    val deathday: String = "",
    val gender: String = "",
    val id: Int = 0,
    val image: NetworkImage = NetworkImage(),
    val name: String = ""
)