package ru.resodostudios.flick.feature.search.domain.repository

import retrofit2.Response
import ru.resodostudios.flick.feature.search.data.model.SearchedMovie
import ru.resodostudios.flick.feature.search.data.model.SearchedPeople

interface SearchRepository {

    suspend fun searchMovies(query: String): Response<List<SearchedMovie>>

    suspend fun searchPeople(query: String): Response<List<SearchedPeople>>
}