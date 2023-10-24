package ru.resodostudios.flick.core.data.model

import ru.resodostudios.flick.core.database.model.FavoritePersonEntity
import ru.resodostudios.flick.core.model.data.FavoritePerson

fun FavoritePerson.asEntity() = FavoritePersonEntity(
    id = id,
    image = image,
    name = name
)