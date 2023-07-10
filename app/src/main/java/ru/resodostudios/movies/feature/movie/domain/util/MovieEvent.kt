package ru.resodostudios.movies.feature.movie.domain.util

sealed interface MovieEvent {

    data class GetMovie(val id: Int) : MovieEvent
}