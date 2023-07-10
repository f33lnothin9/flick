package ru.resodostudios.movies.feature.movie.domain.util

import ru.resodostudios.movies.feature.movie.data.model.Movie

data class MovieState(
    val movie: Movie = Movie(),
    val isLoading: Boolean = true,
    val isError: Boolean = false
)
