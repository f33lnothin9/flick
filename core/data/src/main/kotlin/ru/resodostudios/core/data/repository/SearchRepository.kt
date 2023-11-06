package ru.resodostudios.core.data.repository

import kotlinx.coroutines.flow.Flow
import ru.resodostudios.flick.core.model.data.SearchMovie
import ru.resodostudios.flick.core.model.data.SearchPerson

interface SearchRepository {

    fun searchMovies(query: String): Flow<List<SearchMovie>>
}