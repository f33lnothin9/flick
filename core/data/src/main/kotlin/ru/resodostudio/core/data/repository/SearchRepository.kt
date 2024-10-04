package ru.resodostudio.core.data.repository

import kotlinx.coroutines.flow.Flow
import ru.resodostudio.flick.core.model.data.SearchMovie

interface SearchRepository {

    fun searchMovies(query: String): Flow<List<SearchMovie>>
}