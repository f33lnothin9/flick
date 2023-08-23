package ru.resodostudios.flick.core.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "favorite_movies"
)
data class FavoriteMovieEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int? = null,
    val image: String? = null,
    val name: String? = null,
    val rating: Double? = null
)