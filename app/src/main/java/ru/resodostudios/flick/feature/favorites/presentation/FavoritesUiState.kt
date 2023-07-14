package ru.resodostudios.flick.feature.favorites.presentation

import ru.resodostudios.flick.feature.favorites.domain.model.FavoriteMovie

data class FavoritesUiState(
    val movies: List<FavoriteMovie> = emptyList()
)