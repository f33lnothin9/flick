package ru.resodostudios.flick.feature.movies.presentation

import ru.resodostudios.flick.feature.movies.data.model.MovieEntry
import ru.resodostudios.flick.feature.search.data.model.SearchedMovie

data class MoviesUiState(
    val movies: List<MovieEntry> = emptyList(),
    val searchedMovies: List<SearchedMovie> = emptyList(),
    val isLoading: Boolean = true,
    val isError: Boolean = false
)