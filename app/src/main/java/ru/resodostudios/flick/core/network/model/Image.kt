package ru.resodostudios.flick.core.network.model

import kotlinx.serialization.Serializable

@Serializable
data class Image(
    val medium: String? = null,
    val original: String? = null
)