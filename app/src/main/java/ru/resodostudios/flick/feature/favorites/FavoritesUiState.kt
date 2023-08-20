package ru.resodostudios.flick.feature.favorites

import ru.resodostudios.flick.feature.favorites.domain.model.FavoriteMovie

data class FavoritesUiState(
    val movies: List<FavoriteMovie> = emptyList()
)