package ru.resodostudios.flick.core.network.model

import kotlinx.serialization.Serializable

@Serializable
data class Country(
    val name: String? = null,
    val code: String? = null,
    val timezone: String? = null
)