package ru.resodostudios.flick.feature.favorites

import ru.resodostudios.flick.core.database.model.FavoriteMovieEntity

data class FavoritesUiState(
    val movies: List<FavoriteMovieEntity> = emptyList()
)