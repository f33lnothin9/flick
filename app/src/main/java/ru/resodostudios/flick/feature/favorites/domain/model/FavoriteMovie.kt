package ru.resodostudios.flick.feature.favorites.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FavoriteMovie(
    val image: String? = null,
    val name: String? = null,
    val rating: Double? = null,
    @PrimaryKey(autoGenerate = false)
    val id: Int? = null
)