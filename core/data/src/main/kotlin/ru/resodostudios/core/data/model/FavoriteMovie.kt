package ru.resodostudios.core.data.model

import ru.resodostudios.flick.core.database.model.FavoriteMovieEntity
import ru.resodostudios.flick.core.model.data.FavoriteMovie

fun FavoriteMovie.asEntity() = FavoriteMovieEntity(
    id = id,
    image = image,
    name = name,
    rating = rating,
    genres = genres
)