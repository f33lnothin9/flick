package ru.resodostudios.flick.feature.people.presentation

import ru.resodostudios.flick.feature.people.domain.model.People

data class PeopleUiState(
    val people: List<People> = emptyList(),
    val isLoading: Boolean = true,
    val isError: Boolean = false
)