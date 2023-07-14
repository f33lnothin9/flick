package ru.resodostudios.movies.feature.search.domain.repository

import retrofit2.Response
import ru.resodostudios.movies.feature.search.data.model.SearchedMovie

interface SearchRepository {

    suspend fun searchMovies(query: String): Response<List<SearchedMovie>>
}