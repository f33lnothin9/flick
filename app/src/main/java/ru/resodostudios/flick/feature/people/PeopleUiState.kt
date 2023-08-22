package ru.resodostudios.flick.feature.people

import ru.resodostudios.flick.core.model.data.Person

sealed interface PeopleUiState {

    data object Loading : PeopleUiState

    data class Success(
        val people: List<Person>
    ) : PeopleUiState

    data class Error(
        val errorMessage: String
    ) : PeopleUiState
}