package ru.resodostudios.movies.feature.movie.presentation

import ru.resodostudios.movies.feature.movie.data.model.Movie

data class MovieUiState(
    val movie: Movie = Movie(),
    val isLoading: Boolean = true,
    val isError: Boolean = false
)
