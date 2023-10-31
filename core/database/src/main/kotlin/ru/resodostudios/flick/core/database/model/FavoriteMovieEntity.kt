package ru.resodostudios.flick.core.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.resodostudios.flick.core.model.data.FavoriteMovie

@Entity(
    tableName = "favorite_movies"
)
data class FavoriteMovieEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val image: String,
    val name: String,
    val rating: Double?,
    val genres: List<String>?
)

fun FavoriteMovieEntity.asExternalModel() = FavoriteMovie(
    id = id,
    name = name,
    image = image,
    rating = rating,
    genres = genres
)