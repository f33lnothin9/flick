package ru.resodostudios.flick.core.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Schedule(
    val days: List<String>? = null,
    val time: String? = null
)