package ru.resodostudios.flick.feature.people.presentation

import ru.resodostudios.flick.feature.people.domain.model.People
import ru.resodostudios.flick.feature.search.data.model.SearchedPeople

data class PeopleUiState(
    val people: List<People> = emptyList(),
    val searchedPeople: List<SearchedPeople> = emptyList(),
    val isLoading: Boolean = true,
    val isError: Boolean = false
)