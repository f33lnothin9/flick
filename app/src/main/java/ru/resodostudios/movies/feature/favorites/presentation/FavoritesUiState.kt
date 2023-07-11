package ru.resodostudios.movies.feature.favorites.presentation

import ru.resodostudios.movies.feature.favorites.domain.model.FavoriteMovie

data class FavoritesUiState(
    val movies: List<FavoriteMovie> = emptyList()
)