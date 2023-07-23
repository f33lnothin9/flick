package ru.resodostudios.flick.feature.people.domain.util

sealed interface PeopleEvent {

    data class Search(val query: String) : PeopleEvent
}