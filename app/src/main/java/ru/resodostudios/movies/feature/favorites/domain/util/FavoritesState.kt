package ru.resodostudios.movies.feature.favorites.domain.util

import ru.resodostudios.movies.feature.favorites.domain.model.FavoriteMovie

data class FavoritesState(
    val movies: List<FavoriteMovie> = emptyList()
)