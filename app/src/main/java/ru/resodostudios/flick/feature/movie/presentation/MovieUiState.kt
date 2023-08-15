package ru.resodostudios.flick.feature.movie.presentation

import ru.resodostudios.flick.core.network.model.Cast
import ru.resodostudios.flick.core.network.model.Crew
import ru.resodostudios.flick.core.network.model.Movie

data class MovieUiState(
    val movie: Movie = Movie(),
    val cast: List<Cast> = emptyList(),
    val crew: List<Crew> = emptyList(),
    val isLoading: Boolean = true,
    val isError: Boolean = false
)