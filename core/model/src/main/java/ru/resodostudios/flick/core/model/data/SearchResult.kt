package ru.resodostudios.flick.core.model.data

data class SearchResult(
    val movies: List<SearchMovie>,
    val people: List<SearchPerson>
)