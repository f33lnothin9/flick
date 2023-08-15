package ru.resodostudios.flick.feature.movies.presentation

import ru.resodostudios.flick.core.network.model.Movie
import ru.resodostudios.flick.feature.search.data.model.SearchedMovie

data class MoviesUiState(
    val movies: List<Movie> = emptyList(),
    val searchedMovies: List<SearchedMovie> = emptyList(),
    val isLoading: Boolean = true,
    val isError: Boolean = false
)