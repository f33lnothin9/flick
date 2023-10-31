package ru.resodostudios.flick.core.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.resodostudios.flick.core.model.data.FavoritePerson

@Entity(
    tableName = "favorite_people"
)
data class FavoritePersonEntity(
    @PrimaryKey
    val id: Int,
    val image: String,
    val name: String
)

fun FavoritePersonEntity.asExternalModel() = FavoritePerson(
    id = id,
    name = name,
    image = image
)