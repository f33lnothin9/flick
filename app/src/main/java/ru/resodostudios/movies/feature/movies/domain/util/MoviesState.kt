package ru.resodostudios.movies.feature.movies.domain.util

import ru.resodostudios.movies.feature.movies.data.model.MovieEntry

data class MoviesState(
    val movies: List<MovieEntry> = emptyList(),
    val isLoading: Boolean = true,
    val isError: Boolean = false
)