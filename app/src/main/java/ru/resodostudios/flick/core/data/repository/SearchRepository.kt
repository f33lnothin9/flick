package ru.resodostudios.flick.core.data.repository

import kotlinx.coroutines.flow.Flow
import ru.resodostudios.flick.core.model.data.SearchMovie
import ru.resodostudios.flick.core.model.data.SearchPeople

interface SearchRepository {

    fun searchMovies(query: String): Flow<List<SearchMovie>>

    fun searchPeople(query: String): Flow<List<SearchPeople>>
}