package ru.resodostudios.flick.feature.people

import ru.resodostudios.flick.feature.people.domain.model.Person
import ru.resodostudios.flick.feature.search.data.model.SearchedPeople

data class PeopleUiState(
    val people: List<Person> = emptyList(),
    val searchedPeople: List<SearchedPeople> = emptyList(),
    val isLoading: Boolean = true,
    val isError: Boolean = false
)