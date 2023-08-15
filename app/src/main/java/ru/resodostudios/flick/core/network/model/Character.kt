package ru.resodostudios.flick.core.network.model

import kotlinx.serialization.Serializable
import ru.resodostudios.flick.core.network.model.Image

@Serializable
data class Character(
    val id: Int? = null,
    val image: Image? = null,
    val name: String? = null
)