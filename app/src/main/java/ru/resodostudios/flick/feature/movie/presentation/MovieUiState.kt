package ru.resodostudios.flick.feature.movie.presentation

import ru.resodostudios.flick.feature.movie.data.model.Movie

data class MovieUiState(
    val movie: Movie = Movie(),
    val isLoading: Boolean = true,
    val isError: Boolean = false
)
