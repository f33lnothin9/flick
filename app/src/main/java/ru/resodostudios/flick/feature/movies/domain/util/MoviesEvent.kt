package ru.resodostudios.flick.feature.movies.domain.util

sealed interface MoviesEvent {

    data class Search(val query: String) : MoviesEvent
}