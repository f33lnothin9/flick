package ru.resodostudios.movies.feature.movies.domain.util

sealed interface MoviesEvent {

    data class Search(val query: String) : MoviesEvent
}