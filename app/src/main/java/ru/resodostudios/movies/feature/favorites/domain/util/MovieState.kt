package ru.resodostudios.movies.feature.favorites.domain.util

import ru.resodostudios.movies.feature.favorites.domain.model.FavoriteMovie
import ru.resodostudios.movies.feature.movie.data.model.Movie

data class MovieState(
    val movies: List<FavoriteMovie> = emptyList()
)