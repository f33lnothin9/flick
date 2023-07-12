package ru.resodostudios.movies.feature.movies.presentation

import ru.resodostudios.movies.feature.movies.data.model.MovieEntry
import ru.resodostudios.movies.feature.search.data.model.SearchedMovie

data class MoviesUiState(
    val movies: List<MovieEntry> = emptyList(),
    val searchedMovies: List<SearchedMovie> = emptyList(),
    val isLoading: Boolean = true,
    val isError: Boolean = false
)