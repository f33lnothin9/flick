package ru.resodostudios.movies.feature.movies.presentation

import ru.resodostudios.movies.feature.movies.data.model.MovieEntry

data class MoviesUiState(
    val movies: List<MovieEntry> = emptyList(),
    val isLoading: Boolean = true,
    val isError: Boolean = false
)