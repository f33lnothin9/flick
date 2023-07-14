package ru.resodostudios.flick.feature.search.domain.repository

import retrofit2.Response
import ru.resodostudios.flick.feature.search.data.model.SearchedMovie

interface SearchRepository {

    suspend fun searchMovies(query: String): Response<List<SearchedMovie>>
}